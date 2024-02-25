package com.weight.bridge.domain.repository

import com.weight.bridge.data.local.dao.BridgeTicketDao
import kotlinx.coroutines.flow.Flow

interface RealmRepository {

    suspend fun createTicket(bridgeTicketDao: BridgeTicketDao)
    suspend fun editTicket(primaryKey: String, bridgeTicketDao: BridgeTicketDao)
    suspend fun getTicket(id: String): BridgeTicketDao
    fun getAllTicket(): Flow<List<BridgeTicketDao>>
}