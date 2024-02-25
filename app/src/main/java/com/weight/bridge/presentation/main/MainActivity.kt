package com.weight.bridge.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.weight.bridge.presentation.graph.Route
import com.weight.bridge.presentation.theme.WightBridgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WightBridgeTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.MainNav.route) {
                    navigation(
                        route = Route.MainNav.route,
                        startDestination = Route.ListScreen.route
                    ) {
                        composable(Route.ListScreen.route) {

                        }
                        composable(Route.AddScreen.route) {

                        }
                        composable(Route.EditScreen.route) {

                        }
                    }
                }
            }
        }
    }
}

