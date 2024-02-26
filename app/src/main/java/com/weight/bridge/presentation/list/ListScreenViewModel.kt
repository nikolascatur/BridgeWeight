package com.weight.bridge.presentation.list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.manager.RepositoryManager
import com.weight.bridge.util.convertDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val manager: RepositoryManager
) : ViewModel() {
    private val _state = mutableStateOf(ListScreenState())
    val state: State<ListScreenState> = _state

    fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.DeleteAction -> deleteTicket(event.dao)
            is ListScreenEvent.FilterAction -> filterAction(event.filter)
            is ListScreenEvent.SortingAction -> sortingAction(event.sorting)
        }
    }

    fun sortingAction(sortBy: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                sorting = sortBy
            )
            getAllTicket()
        }
    }

    fun filterAction(filter: String) {
        val result = _state.value.listItem.filter { item ->
            val date = item.timeEnter.convertDate().lowercase()
            val filterLower = filter.lowercase()
            date.contains(filterLower) || item.driverName.lowercase()
                .contains(filterLower) || item.truckLicenseNumber.lowercase().contains(filterLower)
        }
        _state.value = _state.value.copy(filtering = result)
    }

    fun deleteTicket(dao: BridgeTicketDao) {
        viewModelScope.launch {
            manager.deleteTicket(dao)
        }
    }

    fun getAllTicket() {
        viewModelScope.launch {
            manager.getAllTicket(state.value.sorting).collectLatest { value ->
                _state.value = _state.value.copy(listItem = value)
            }
        }
    }
}