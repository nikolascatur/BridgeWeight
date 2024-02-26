package com.weight.bridge.domain.manager

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.data.remote.model.BridgeTicket
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.repository.FirebaseRepository
import com.weight.bridge.domain.repository.RealmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class RepositoryManager(
    private val realmRepository: RealmRepository,
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun createTicket(bridgeTicketDto: BridgeTicketDto) {
        val dao = toBridgeTicketDao(bridgeTicketDto)
        realmRepository.createTicket(dao)
        firebaseRepository.saveTicket(bridgeTicketDto.toBridgeTicket(dao.primaryCode))
    }

    suspend fun saveToLocal(bridgeTicketDto: BridgeTicketDto) {
        val dao = toBridgeTicketDao(bridgeTicketDto)
        realmRepository.createTicket(dao)
    }

    suspend fun editTicket(bridgeTicketDto: BridgeTicketDto) {
        realmRepository.editTicket(bridgeTicketDto.primaryCode, toBridgeTicketDao(bridgeTicketDto))
        firebaseRepository.saveTicket(bridgeTicketDto.toBridgeTicket(bridgeTicketDto.primaryCode))
    }

    suspend fun getTicket(id: String): BridgeTicketDto? =
        realmRepository.getTicket(id)?.toBridgetTicketDto()

    suspend fun deleteTicket(dao: BridgeTicketDao) {
        firebaseRepository.removeTicket(dao.primaryCode)
        realmRepository.deleteTicket(dao)
    }

    fun getAllTicket(sortBy: String): Flow<List<BridgeTicketDao>> =
        realmRepository.getAllTicket(sortBy)

    fun syncFromServer(parsing: (BridgeTicketDto) -> Unit) {
        firebaseRepository.getDatabasePreference().child("data")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { value ->
                        value.getValue(BridgeTicket::class.java)?.let { data ->
                            parsing(data.toBridgeTicketDto())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

}