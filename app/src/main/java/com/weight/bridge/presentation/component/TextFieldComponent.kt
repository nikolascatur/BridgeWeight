package com.weight.bridge.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weight.bridge.presentation.theme.WightBridgeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldRow(
    text: String,
    isEnable: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
    value: String,
    onChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier.padding(bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = isEnable,
            value = value,
            keyboardOptions = keyboardOptions,
            onValueChange = onChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldRowKg(
    text: String,
    isEnable: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    value: String,
    onChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier.padding(bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            keyboardOptions = keyboardOptions,
            onValueChange = onChange,
            enabled = isEnable,
            trailingIcon = {
                Text(text = "Kg", style = MaterialTheme.typography.titleLarge)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewTextFieldRowKg() {
    WightBridgeTheme {
        TextFieldRowKg(text = "Inbound Weight", isEnable = false, value = "", onChange = {

        })
    }
}