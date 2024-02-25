package com.weight.bridge.presentation.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.presentation.graph.Route
import com.weight.bridge.presentation.list.component.ListItemComponent
import com.weight.bridge.presentation.list.component.SearchBar
import com.weight.bridge.presentation.theme.WightBridgeTheme
import com.weight.bridge.util.Constant

@Composable
fun ListScreen(
    navHostController: NavHostController,
    tickets: List<BridgeTicketDao>,
    event: (ListScreenEvent) -> Unit
) {
    Scaffold(topBar = {
        SearchBar(
            modifier = Modifier.padding(20.dp),
            text = "Search",
            readOnly = false,
            onValueChange = {}) {

        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navigateToDetail(
                navHostController,
                "",
                Constant.ADD_MODE
            )
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
        }
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(top = padding.calculateTopPadding()), content = {
            items(tickets.size) {
                ListItemComponent(bridgeTicket = tickets[it], index = it) { mode ->
                    if (mode != Constant.DELETE_MODE) {
                        navigateToDetail(navHostController, tickets[it].primaryCode, mode)
                    } else {
                        event(ListScreenEvent.DeleteAction(tickets[it]))
                    }
                }
            }
        })
    }
}

private fun navigateToDetail(
    navHostController: NavHostController,
    primaryCode: String,
    mode: Int
) {
    with(navHostController) {
        currentBackStackEntry?.savedStateHandle?.set(Constant.BUNDLE_ID, primaryCode)
        currentBackStackEntry?.savedStateHandle?.set(Constant.BUNDLE_MODE, mode)
        navigate(Route.AddScreen.route)
    }
}

@Preview
@Composable
fun previewListScreen() {
    WightBridgeTheme {
        ListScreen(NavHostController(LocalContext.current), emptyList(), {})
    }
}