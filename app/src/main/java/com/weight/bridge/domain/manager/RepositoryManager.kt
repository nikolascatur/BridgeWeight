package com.weight.bridge.domain.manager

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.repository.FirebaseRepository
import com.weight.bridge.domain.repository.RealmRepository
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

    suspend fun editTicket(bridgeTicketDto: BridgeTicketDto) {
        realmRepository.editTicket(bridgeTicketDto.primaryCode, toBridgeTicketDao(bridgeTicketDto))
        firebaseRepository.saveTicket(bridgeTicketDto.toBridgeTicket(bridgeTicketDto.primaryCode))
    }

    suspend fun getTicket(id: String): BridgeTicketDto? =
        realmRepository.getTicket(id)?.toBridgetTicketDto()

    suspend fun deleteTicket(dao: BridgeTicketDao) {
        realmRepository.deleteTicket(dao)
        firebaseRepository.removeTicket(dao.primaryCode)
    }

    fun getAllTicket(sortBy: String): Flow<List<BridgeTicketDao>> =
        realmRepository.getAllTicket(sortBy)

}