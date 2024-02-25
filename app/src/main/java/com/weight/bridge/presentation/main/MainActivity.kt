package com.weight.bridge.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.weight.bridge.presentation.add.AddScreen
import com.weight.bridge.presentation.add.AddScreenViewModel
import com.weight.bridge.presentation.graph.Route
import com.weight.bridge.presentation.list.ListScreen
import com.weight.bridge.presentation.list.ListScreenViewModel
import com.weight.bridge.presentation.theme.WightBridgeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WightBridgeTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val context = LocalContext.current
                NavHost(navController = navController, startDestination = Route.MainNav.route) {
                    navigation(
                        route = Route.MainNav.route,
                        startDestination = Route.ListScreen.route
                    ) {
                        composable(Route.ListScreen.route) {
                            val viewModel: ListScreenViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            ListScreen(navController, state.listItem)
                        }
                        composable(Route.AddScreen.route) {
                            val viewModel: AddScreenViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            AddScreen(
                                navHostController = navController,
                                context = context,
                                state = state,
                                event = viewModel::onEvent
                            )
                        }
                        composable(Route.EditScreen.route) {

                        }
                    }
                }
            }
        }
    }
}

