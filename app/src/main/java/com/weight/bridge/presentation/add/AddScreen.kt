package com.weight.bridge.presentation.add

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.weight.bridge.R
import com.weight.bridge.presentation.component.TextFieldRow
import com.weight.bridge.presentation.component.TextFieldRowKg
import com.weight.bridge.presentation.component.ToolbarBackButton
import com.weight.bridge.presentation.theme.WightBridgeTheme

@Composable
fun AddScreen(
    navHostController: NavHostController,
    context: Context,
    state: AddScreenState,
    event: (AddScreenEvent) -> Unit
) {

    ToolbarBackButton(
        navHostController = navHostController,
        title = context.getString(R.string.title_add)
    ) { padding ->
        Column(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                start = 10.dp,
                end = 10.dp
            )
        ) {
            TextFieldRow(
                context.getString(R.string.label_time_enter),
                value = state.timeEnter.toString(),
                onChange = {

                })
            TextFieldRow(
                context.getString(R.string.label_truck_license_number),
                value = state.truckLicenseNumber,
                onChange = {
                    event(AddScreenEvent.InputData(state.copy(truckLicenseNumber = it)))
                }
            )
            TextFieldRow(
                context.getString(R.string.label_driver_name),
                value = state.driverName,
                onChange = {
                    event(AddScreenEvent.InputData(state.copy(driverName = it)))
                })
            TextFieldRowKg(
                context.getString(R.string.label_inbound_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = state.inboundWeight,
                onChange = {
                    event(AddScreenEvent.InputData(state.copy(inboundWeight = it)))
                }
            )
            TextFieldRowKg(context.getString(R.string.label_outbound_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = state.inboundWeight,
                onChange = {
                    event(AddScreenEvent.InputData(state.copy(inboundWeight = it)))
                })
            TextFieldRowKg(context.getString(R.string.label_net_weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isEnable = false,
                value = state.netWeight,
                onChange = {})

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { event(AddScreenEvent.SubmitData) },
                    enabled = state.isEnableButton
                ) {
                    Text(text = context.getString(R.string.label_create_ticket))
                }
            }


        }
    }
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