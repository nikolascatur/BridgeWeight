package com.weight.bridge.presentation.list

import com.weight.bridge.data.local.dao.BridgeTicketDao

sealed class ListScreenEvent {
    data class DeleteAction(val dao: BridgeTicketDao) : ListScreenEvent()
    data class FilterAction(val filter: String) : ListScreenEvent()
}