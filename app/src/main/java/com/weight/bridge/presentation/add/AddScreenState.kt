package com.weight.bridge.presentation.add

import com.weight.bridge.util.Constant

data class AddScreenState(
    val timeEnter: Long = System.currentTimeMillis(),
    val truckLicenseNumber: String = "",
    val driverName: String = "",
    val inboundWeight: String = "",
    val outboundWeight: String = "",
    val netWeight: String = "",
    val mode: Int = Constant.ADD_MODE,
    val primaryCode: String = "",

    val isSubmitSuccess: Boolean = false,
    val isEnableButton: Boolean = false,
    val isError: Exception? = null
)