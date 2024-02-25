package com.weight.bridge.presentation.add

sealed class AddScreenEvent {
    data class InputData(val state: AddScreenState) : AddScreenEvent()

    data class SetMode(val state: AddScreenState) : AddScreenEvent()
    object GetData : AddScreenEvent()

    object SaveData : AddScreenEvent()
    object SubmitData : AddScreenEvent()
}