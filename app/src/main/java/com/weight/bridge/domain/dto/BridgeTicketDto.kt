package com.weight.bridge.domain.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BridgeTicketDto(
    val primaryCode: String = "",
    val timeEnter: Long = 0L,
    val truckLicenseNumber: String = "",
    val driverName: String = "",
    val inboundWeight: String = "",
    val outboundWeight: String = ""
) : Parcelable