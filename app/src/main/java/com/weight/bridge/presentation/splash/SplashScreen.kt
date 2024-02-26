package com.weight.bridge.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.weight.bridge.presentation.graph.Route

@Composable
fun SplashScreen(navHostController: NavHostController, state: State<Boolean>) {

    LaunchedEffect(key1 = state) {
        if (state.value) {
            navHostController.navigate(Route.ListScreen.route) {
                launchSingleTop = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Filled.Face,
            contentDescription = null
        )
    }

}