package com.weight.bridge.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.manager.RepositoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val manager: RepositoryManager
) : ViewModel() {
    private val _state = MutableStateFlow(ListScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: ListScreenEvent) {
        when(event) {
            is ListScreenEvent.DeleteAction -> deleteTicket(event.dao)
        }
    }

    fun deleteTicket(dao: BridgeTicketDao) {
        viewModelScope.launch {
            manager.deleteTicket(dao)
        }
    }

    fun getAllTicket() {
        viewModelScope.launch {
            manager.getAllTicket().collectLatest { value ->
                _state.update { currentState ->
                    currentState.copy(listItem = value)
                }
            }
        }
    }

}