package com.weight.bridge.presentation.add

import com.weight.bridge.domain.dto.BridgeTicketDto

sealed class AddScreenEvent {
    data class InputData(val state: AddScreenState) : AddScreenEvent()

    data class SetMode(val state: AddScreenState) : AddScreenEvent()
    object GetData : AddScreenEvent()

    data class SaveData(val dto: BridgeTicketDto) : AddScreenEvent()
    object SubmitData : AddScreenEvent()
}