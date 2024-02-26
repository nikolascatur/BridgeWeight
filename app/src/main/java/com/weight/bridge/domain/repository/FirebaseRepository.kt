package com.weight.bridge.domain.repository

import com.weight.bridge.data.remote.model.BridgeTicket

interface FirebaseRepository {
    fun getAllTicket()
    fun removeTicket(primaryCode: String)
    fun saveTicket(bridgeTicket: BridgeTicket)
}