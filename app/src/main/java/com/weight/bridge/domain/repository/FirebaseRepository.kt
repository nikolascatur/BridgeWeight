package com.weight.bridge.domain.repository

import com.google.firebase.database.DatabaseReference
import com.weight.bridge.data.remote.model.BridgeTicket

interface FirebaseRepository {

    fun getDatabasePreference(): DatabaseReference
    fun removeTicket(primaryCode: String)
    fun saveTicket(bridgeTicket: BridgeTicket)

    fun getTicket(primaryCode: String): String?
}