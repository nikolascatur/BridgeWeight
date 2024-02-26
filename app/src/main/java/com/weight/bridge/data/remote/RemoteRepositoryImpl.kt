package com.weight.bridge.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.weight.bridge.data.remote.model.BridgeTicket
import com.weight.bridge.domain.repository.FirebaseRepository
import com.weight.bridge.util.Constant

class RemoteRepositoryImpl: FirebaseRepository {

    private fun getDatabasePreference() = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.DATABASE_URL)

    override fun getAllTicket() {
        val list = arrayListOf<BridgeTicket>()
        getDatabasePreference().child("data").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { value ->
                    value.getValue(BridgeTicket::class.java)?.let {
                        list.add(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun removeTicket(primaryCode: String) {
        getDatabasePreference().child("data").child(primaryCode).removeValue()
    }

    override fun saveTicket(bridgeTicket: BridgeTicket) {
        getDatabasePreference().child("data").child(bridgeTicket.primaryCode).setValue(bridgeTicket)
    }

}