package com.weight.bridge.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.weight.bridge.presentation.theme.WightBridgeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarBackButton(
    navHostController: NavHostController,
    title: String,
    composable: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = title) }, navigationIcon = {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
    }, content = composable)
}

@Preview
@Composable
fun previewBackButton() {
    WightBridgeTheme {
        ToolbarBackButton(
            navHostController = NavHostController(LocalContext.current),
            title = "Judul"
        ) {

        }
    }
}