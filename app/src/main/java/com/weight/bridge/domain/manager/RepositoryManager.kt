package com.weight.bridge.domain.manager

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.repository.RealmRepository
import kotlinx.coroutines.flow.Flow

class RepositoryManager(private val realmRepository: RealmRepository) {

    suspend fun createTicket(bridgeTicketDto: BridgeTicketDto) {
        realmRepository.createTicket(toBridgeTicketDao(bridgeTicketDto))
    }

    suspend fun editTicket(bridgeTicketDto: BridgeTicketDto) {
        realmRepository.editTicket(bridgeTicketDto.primaryCode, toBridgeTicketDao(bridgeTicketDto))
    }

    suspend fun getTicket(id: String): BridgeTicketDto? =
        realmRepository.getTicket(id)?.toBridgetTicketDto()

    suspend fun deleteTicket(dao: BridgeTicketDao) = realmRepository.deleteTicket(dao)

    fun getAllTicket(sortBy: String): Flow<List<BridgeTicketDao>> = realmRepository.getAllTicket(sortBy)

}