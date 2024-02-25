package com.weight.bridge.presentation.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.manager.RepositoryManager
import com.weight.bridge.util.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(private val repository: RepositoryManager) :
    ViewModel() {

    private val _state = mutableStateOf(AddScreenState())
    val state: State<AddScreenState> = _state

    fun onEvent(event: AddScreenEvent) {
        when (event) {
            is AddScreenEvent.InputData -> {
                _state.value = _state.value.copy(
                    timeEnter = event.state.timeEnter,
                    truckLicenseNumber = event.state.truckLicenseNumber,
                    driverName = event.state.driverName,
                    inboundWeight = event.state.inboundWeight,
                    outboundWeight = event.state.outboundWeight,
                    isEnableButton = event.state.truckLicenseNumber.isNotEmpty() && event.state.driverName.isNotEmpty() && event.state.inboundWeight.isNotEmpty() && event.state.outboundWeight.isNotEmpty()
                )
            }

            is AddScreenEvent.SetMode -> {
                _state.value = _state.value.copy(
                    mode = event.state.mode,
                    primaryCode = event.state.primaryCode,
                )
            }

            is AddScreenEvent.GetData -> {
                getData(_state.value.primaryCode)
            }

            is AddScreenEvent.SaveData -> {
                saveData(event.dto)
            }

            is AddScreenEvent.SubmitData -> {
                viewModelScope.launch {
                    val currentState = _state.value
                    saveTicket(
                        BridgeTicketDto(
                            timeEnter = currentState.timeEnter,
                            truckLicenseNumber = currentState.truckLicenseNumber,
                            driverName = currentState.driverName,
                            inboundWeight = currentState.inboundWeight,
                            outboundWeight = currentState.outboundWeight
                        )
                    )
                }
            }
        }
    }

    fun getData(primaryCode: String) {
        viewModelScope.launch {
            val dto = repository.getTicket(primaryCode)
            _state.value = _state.value.copy(
                timeEnter = dto?.timeEnter.orZero(),
                truckLicenseNumber = dto?.truckLicenseNumber.orEmpty(),
                driverName = dto?.driverName.orEmpty(),
                inboundWeight = dto?.inboundWeight.orEmpty(),
                outboundWeight = dto?.outboundWeight.orEmpty(),
            )
            _state.value = _state.value.copy(isGetData = true)
        }
    }

    fun saveData(dto: BridgeTicketDto) {
        viewModelScope.launch {
            repository.editTicket(
                dto
            )
            _state.value = _state.value.copy(isSubmitSuccess = true)
        }
    }

    fun saveTicket(ticket: BridgeTicketDto) {
        viewModelScope.launch {
            try {
                repository.createTicket(ticket)
                _state.value = _state.value.copy(isSubmitSuccess = true)
            } catch (ex: Exception) {
                _state.value = _state.value.copy(
                    isError = ex
                )
            }
        }
    }
}