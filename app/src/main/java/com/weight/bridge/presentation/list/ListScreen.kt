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

@Composable
fun ListScreen(navHostController: NavHostController, tickets: List<BridgeTicketDao>) {
    Scaffold(topBar = {
        SearchBar(
            modifier = Modifier.padding(20.dp),
            text = "Search",
            readOnly = false,
            onValueChange = {}) {

        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navHostController.navigate(Route.AddScreen.route) }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
        }
    }) { padding ->
        LazyColumn(content = {
            items(tickets.size) {
                ListItemComponent(bridgeTicket = tickets[it], index = it)
            }
        })
    }

}

@Preview
@Composable
fun previewListScreen() {
    WightBridgeTheme {
        ListScreen(NavHostController(LocalContext.current), emptyList())
    }
}