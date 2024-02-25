package com.weight.bridge.data.local.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class BridgeTicketDao : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var timeEnter: Long = 0L
    var truckLicenseNumber: String = ""
    var driverName: String = ""
    var inboundWeight: Double = 0.0
    var outboundWeight: Double = 0.0
    var primaryCode: String = _id.toHexString()
}