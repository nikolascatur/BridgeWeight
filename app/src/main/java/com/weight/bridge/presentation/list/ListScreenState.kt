package com.weight.bridge.presentation.list

import com.weight.bridge.data.local.dao.BridgeTicketDao

data class ListScreenState(
    val listItem: List<BridgeTicketDao> = emptyList(),
    val search: String = "",
    val filtering: List<BridgeTicketDao> = emptyList(),
    val sorting: String = ""
)