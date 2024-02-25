package com.weight.bridge.presentation.list.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.presentation.theme.Pink80
import com.weight.bridge.presentation.theme.Purple80
import com.weight.bridge.presentation.theme.WightBridgeTheme
import com.weight.bridge.util.Constant
import com.weight.bridge.util.convertDate
import com.weight.bridge.util.toNormalizeString

@Composable
fun ListItemComponent(
    bridgeTicket: BridgeTicketDao,
    index: Int,
    clickItem: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                clickItem(Constant.VIEW_MODE)
            }, colors = CardDefaults.cardColors(
            if (index % 2 == 0) {
                Purple80
            } else {
                Pink80
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row {
                Text(
                    text = "${bridgeTicket.truckLicenseNumber} - ",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = bridgeTicket.driverName,
                    style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(modifier = Modifier.clickable {
                    clickItem(Constant.EDIT_MODE)
                }, imageVector = Icons.Filled.Edit, contentDescription = null)
                Icon(modifier = Modifier.clickable {
                    clickItem(Constant.DELETE_MODE)
                }, imageVector = Icons.Filled.Delete, contentDescription = null)
            }
            Canvas(modifier = Modifier.fillMaxWidth(), onDraw = {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = 0.dp.toPx(), y = canvasHeight / 2),
                    end = Offset(x = canvasWidth, y = canvasHeight / 2),
                    color = Color.Black,
                    strokeWidth = 1.dp.toPx()
                )
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Inbound: ${bridgeTicket.inboundWeight.toNormalizeString()} kg - Outbound: ${bridgeTicket.outboundWeight.toNormalizeString()}")
            Text(text = bridgeTicket.timeEnter.convertDate())
        }
    }
}

@Preview
@Composable
fun PreviewListItemComponent() {
    WightBridgeTheme {
        ListItemComponent(
            BridgeTicketDao().apply {
                timeEnter = 1708846813812
                truckLicenseNumber = "AB 2300 DD"
                driverName = "Paijo"
                inboundWeight = 100.0
                outboundWeight = 50.0
            }, 1
        ) {

        }
    }
}