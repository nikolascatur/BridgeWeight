package com.weight.bridge.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.weight.bridge.data.remote.model.BridgeTicket
import com.weight.bridge.domain.repository.FirebaseRepository
import com.weight.bridge.util.Constant

class RemoteRepositoryImpl: FirebaseRepository {

    override fun getDatabasePreference(): DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.DATABASE_URL)

    override fun removeTicket(primaryCode: String) {
        getDatabasePreference().child("data").child(primaryCode).removeValue()
    }

    override fun saveTicket(bridgeTicket: BridgeTicket) {
        getDatabasePreference().child("data").child(bridgeTicket.primaryCode).setValue(bridgeTicket)
    }

    override fun getTicket(primaryCode: String): String? = getDatabasePreference().child("data").child(primaryCode).key


}