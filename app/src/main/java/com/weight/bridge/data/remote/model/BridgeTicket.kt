package com.weight.bridge.data.remote.model

data class BridgeTicket(
    val timeEnter: Long = 0L,
    val truckLicenseNumber: String = "",
    val driverName: String = "",
    val inboundWeight: Double = 0.0,
    val outboundWeight: Double = 0.0,
    val primaryCode: String = ""
)
