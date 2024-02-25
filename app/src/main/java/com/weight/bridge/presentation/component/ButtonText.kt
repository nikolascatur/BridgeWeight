package com.weight.bridge.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weight.bridge.presentation.theme.WightBridgeTheme

@Composable
fun ButtonText(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .graphicsLayer {
                shadowElevation = 3.dp.toPx()
                shape = CutCornerShape(10.dp)
                clip = true
            }
            .then(modifier)
            .clickable { onClick() }
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun previewButtonText() {
    WightBridgeTheme() {
        ButtonText(
            modifier = Modifier
                .aspectRatio(1f),
            text = "Add Product"
        ) {

        }
    }
}

