package com.weight.bridge.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.weight.bridge.presentation.add.AddScreen
import com.weight.bridge.presentation.add.AddScreenEvent
import com.weight.bridge.presentation.add.AddScreenViewModel
import com.weight.bridge.presentation.graph.Route
import com.weight.bridge.presentation.list.ListScreen
import com.weight.bridge.presentation.list.ListScreenViewModel
import com.weight.bridge.presentation.theme.WightBridgeTheme
import com.weight.bridge.util.Constant
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
                            viewModel.getAllTicket()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            ListScreen(navController, state.listItem)
                        }
                        composable(Route.AddScreen.route) {
                            val viewModel: AddScreenViewModel = hiltViewModel()
                            val state = viewModel.state.value
                            val args = getArgument(navController)
                            viewModel.onEvent(
                                AddScreenEvent.SetMode(
                                    state.copy(
                                        mode = args.first ?: Constant.ADD_MODE,
                                        primaryCode = args.second.orEmpty()
                                    )
                                )
                            )
                            if (state.mode == Constant.VIEW_MODE || state.mode == Constant.EDIT_MODE) {
                                viewModel.onEvent(AddScreenEvent.GetData)
                            }
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

    fun getArgument(navHostController: NavHostController): Pair<Int?, String?> {
        val pair = navHostController.previousBackStackEntry?.let {
            val mode = it.savedStateHandle.get<Int>(Constant.BUNDLE_MODE)
            val id = it.savedStateHandle.get<String>(Constant.BUNDLE_ID)
            Pair(mode, id)
        } ?: Pair(Constant.ADD_MODE, "")
        return pair
    }
}

