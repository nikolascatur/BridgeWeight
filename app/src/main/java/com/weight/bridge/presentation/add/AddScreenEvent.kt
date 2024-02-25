package com.weight.bridge.presentation.add

sealed class AddScreenEvent {
    data class InputData(val state: AddScreenState) : AddScreenEvent()
    object SubmitData : AddScreenEvent()
}