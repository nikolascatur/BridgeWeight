package com.weight.bridge.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.manager.RepositoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(private val repository: RepositoryManager) :
    ViewModel() {

    private val _state = MutableStateFlow(AddScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddScreenEvent) {
        when (event) {
            is AddScreenEvent.InputData -> {
                _state.update { currentState ->
                    currentState.copy(
                        timeEnter = event.state.timeEnter,
                        truckLicenseNumber = event.state.truckLicenseNumber,
                        driverName = event.state.driverName,
                        inboundWeight = event.state.inboundWeight,
                        outboundWeight = event.state.outboundWeight,
                    )
                }
            }

            is AddScreenEvent.SubmitData -> {
                viewModelScope.launch {
                    _state.collectLatest {
                        saveTicket(
                            BridgeTicketDto(
                                timeEnter = it.timeEnter,
                                truckLicenseNumber = it.truckLicenseNumber,
                                driverName = it.driverName,
                                inboundWeight = it.inboundWeight,
                                outboundWeight = it.outboundWeight
                            )
                        )
                    }

                }
            }
        }
    }

    fun saveTicket(ticket: BridgeTicketDto) {
        viewModelScope.launch {
            try {
                repository.createTicket(ticket)
            } catch (ex: Exception) {
                _state.value = _state.value.copy(
                    isError = ex
                )
            }
        }
    }
}