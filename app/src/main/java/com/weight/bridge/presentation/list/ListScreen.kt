package com.weight.bridge.presentation.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    state: ListScreenState,
    event: (ListScreenEvent) -> Unit,
) {
    val filter = remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        SearchBar(
            modifier = Modifier.padding(20.dp),
            text = filter.value,
            readOnly = false,
            onValueChange = {
                filter.value = it
                event(ListScreenEvent.FilterAction(filter.value))
            }) {

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
            val size = if (filter.value.isEmpty()) {
                tickets.size
            } else {
                state.filtering.size
            }
            val listData = if (filter.value.isEmpty()) {
                tickets
            } else {
                state.filtering
            }
            items(size) {
                ListItemComponent(bridgeTicket = listData[it], index = it) { mode ->
                    if (mode != Constant.DELETE_MODE) {
                        navigateToDetail(navHostController, listData[it].primaryCode, mode)
                    } else {
                        event(ListScreenEvent.DeleteAction(listData[it]))
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
        ListScreen(NavHostController(LocalContext.current), emptyList(), ListScreenState(), {})
    }
}