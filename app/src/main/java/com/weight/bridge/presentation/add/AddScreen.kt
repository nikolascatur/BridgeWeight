package com.weight.bridge.presentation.add

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.weight.bridge.R
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.presentation.component.TextFieldRow
import com.weight.bridge.presentation.component.TextFieldRowKg
import com.weight.bridge.presentation.component.ToolbarBackButton
import com.weight.bridge.presentation.theme.WightBridgeTheme
import com.weight.bridge.util.Constant
import com.weight.bridge.util.convertDate
import com.weight.bridge.util.orZero
import com.weight.bridge.util.toNormalizeDouble
import com.weight.bridge.util.toNormalizeString
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun AddScreen(
    navHostController: NavHostController,
    context: Context,
    state: AddScreenState,
    event: (AddScreenEvent) -> Unit
) {

    LaunchedEffect(key1 = state.isSubmitSuccess) {
        if (state.isSubmitSuccess) {
            navHostController.navigateUp()
        }
    }

    val editState = remember {
        mutableStateOf(BridgeTicketDto())
    }

    LaunchedEffect(key1 = state.isGetData) {
        editState.value = BridgeTicketDto(
            primaryCode = state.primaryCode,
            timeEnter = state.timeEnter,
            truckLicenseNumber = state.truckLicenseNumber,
            driverName = state.driverName,
            inboundWeight = state.inboundWeight,
            outboundWeight = state.outboundWeight,
        )
    }

    val openDialog = remember {
        mutableStateOf<Boolean>(false)
    }
    val scrollable = rememberScrollState()
    ToolbarBackButton(
        navHostController = navHostController,
        title = when (state.mode) {
            Constant.ADD_MODE -> context.getString(R.string.title_add)
            Constant.VIEW_MODE -> context.getString(R.string.title_detail)
            Constant.EDIT_MODE -> context.getString(R.string.title_edit)
            else -> ""
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 10.dp,
                    end = 10.dp
                )
                .verticalScroll(scrollable)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp), text = context.getString(R.string.label_time_enter),
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                value = if (isEditMode(state.mode)) {
                    editState.value.timeEnter.convertDate()
                } else {
                    state.timeEnter.convertDate()
                },
                onValueChange = {},
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            openDialog.value = isCanEditMode(state.mode)
                        })
                }
            )
            DialogPickerDate(openDialog = openDialog.value, onClick = { isOpen, time ->
                openDialog.value = isOpen
                if (isEditMode(state.mode)) {
                    editState.value = editState.value.copy(timeEnter = time.orZero())
                } else {
                    event(
                        AddScreenEvent.InputData(
                            state = state.copy(timeEnter = time.orZero())
                        )
                    )
                }
            })
            TextFieldRow(
                context.getString(R.string.label_truck_license_number),
                value = if (isEditMode(state.mode)) {
                    editState.value.truckLicenseNumber
                } else {
                    state.truckLicenseNumber
                },
                isEnable = isCanEditMode(state.mode),
                onChange = {
                    if (isEditMode(state.mode)) {
                        editState.value = editState.value.copy(truckLicenseNumber = it)
                    } else {
                        event(
                            AddScreenEvent.InputData(
                                state.copy(truckLicenseNumber = it)
                            )
                        )
                    }
                }
            )
            TextFieldRow(
                context.getString(R.string.label_driver_name),
                value = if (isEditMode(state.mode)) {
                    editState.value.driverName
                } else {
                    state.driverName
                },
                isEnable = isCanEditMode(state.mode),
                onChange = {
                    if (isEditMode(state.mode)) {
                        editState.value = editState.value.copy(driverName = it)
                    } else {
                        event(
                            AddScreenEvent.InputData(
                                state.copy(driverName = it)
                            )
                        )
                    }
                })
            TextFieldRowKg(
                context.getString(R.string.label_inbound_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = if (isEditMode(state.mode)) {
                    editState.value.inboundWeight
                } else {
                    state.inboundWeight
                },
                isEnable = isCanEditMode(state.mode),
                onChange = {
                    if (isEditMode(state.mode)) {
                        editState.value = editState.value.copy(inboundWeight = it)
                    } else {
                        event(
                            AddScreenEvent.InputData(
                                state.copy(inboundWeight = it)
                            )
                        )
                    }
                }
            )
            TextFieldRowKg(context.getString(R.string.label_outbound_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = if (isEditMode(state.mode)) {
                    editState.value.outboundWeight
                } else {
                    state.outboundWeight
                },
                isEnable = isCanEditMode(state.mode),
                onChange = {
                    if (isEditMode(state.mode)) {
                        editState.value = editState.value.copy(outboundWeight = it)
                    } else {
                        event(
                            AddScreenEvent.InputData(
                                state.copy(outboundWeight = it)
                            )
                        )
                    }
                })
            TextFieldRowKg(context.getString(R.string.label_net_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isEnable = false,
                value = (state.inboundWeight.toNormalizeDouble() - state.outboundWeight.toNormalizeDouble()).toNormalizeString(),
                onChange = {})

            if (isCanEditMode(state.mode)) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            when (state.mode) {
                                Constant.EDIT_MODE -> event(AddScreenEvent.SaveData(editState.value))
                                Constant.ADD_MODE -> event(AddScreenEvent.SubmitData)
                            }
                        },
                        enabled = isEditMode(state.mode) || state.isEnableButton
                    ) {
                        when (state.mode) {
                            Constant.EDIT_MODE -> Text(text = context.getString(R.string.label_save_ticket))
                            Constant.ADD_MODE -> Text(text = context.getString(R.string.label_create_ticket))
                        }
                    }
                }
            }
        }
    }
}

fun isCanEditMode(mode: Int): Boolean {
    return when (mode) {
        Constant.VIEW_MODE -> false
        else -> true
    }
}

fun isEditMode(mode: Int): Boolean = mode == Constant.EDIT_MODE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogPickerDate(openDialog: Boolean, onClick: (Boolean, Long?) -> Unit) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = { onClick(false, datePickerState.selectedDateMillis) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClick(false, datePickerState.selectedDateMillis)
                    },
                    enabled = true
                ) {
                    Text("OK")
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        onClick(false, null)
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }

    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun previewAddButton() {
    WightBridgeTheme {
        AddScreen(
            navHostController = NavHostController(LocalContext.current),
            context = LocalContext.current,
            state = AddScreenState(),
            event = {

            }
        )
    }
}