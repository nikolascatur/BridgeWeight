package com.weight.bridge.presentation.add

data class AddScreenState(
    val timeEnter: Long = System.currentTimeMillis(),
    val truckLicenseNumber: String = "",
    val driverName: String = "",
    val inboundWeight: String = "",
    val outboundWeight: String = "",
    val netWeight: String = "",
    val isSubmitSuccess: Boolean = false,
    val isEnableButton: Boolean = false,
    val isError: Exception? = null
)