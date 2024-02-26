package com.weight.bridge.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.presentation.graph.Route
import com.weight.bridge.presentation.list.component.ListItemComponent
import com.weight.bridge.presentation.list.component.SearchBar
import com.weight.bridge.presentation.theme.WightBridgeTheme
import com.weight.bridge.util.Constant
import com.weight.bridge.util.Constant.SORT_BY_DRIVER_NAME
import com.weight.bridge.util.Constant.SORT_BY_LATEST_NEW
import com.weight.bridge.util.Constant.SORT_BY_LICENSE_NUMBER
import com.weight.bridge.util.Constant.SORT_BY_NEW_LATEST

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
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        sortingDialog(state.sorting, sorting = {
            showDialog = false
            event(ListScreenEvent.SortingAction(it))
        })
    }
    Scaffold(topBar = {
        Row(modifier = Modifier.fillMaxWidth()) {
            SearchBar(
                modifier = Modifier
                    .padding(20.dp)
                    .weight(4f),
                text = filter.value,
                readOnly = false,
                onValueChange = {
                    filter.value = it
                    event(ListScreenEvent.FilterAction(filter.value))
                }) {

            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable {
                            showDialog = true
                        }, imageVector = Icons.Filled.Menu, contentDescription = null
                )

            }
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

@Composable
private fun sortingDialog(selected: String, sorting: (String) -> Unit) {
    val listSorting = listOf<String>(
        SORT_BY_NEW_LATEST,
        SORT_BY_LATEST_NEW,
        SORT_BY_DRIVER_NAME,
        SORT_BY_LICENSE_NUMBER
    )
    var selectedDialog by remember {
        mutableStateOf(selected)
    }
    Dialog(onDismissRequest = { sorting(selectedDialog) }) {
        Card(modifier = Modifier.padding(30.dp)) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listSorting.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (item == selectedDialog),
                            onClick = { selectedDialog = item }
                        )
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Button(onClick = { sorting(selectedDialog) }, enabled = !sorting.equals("")) {
                    Text(text = "Apply")
                }
            }
        }

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