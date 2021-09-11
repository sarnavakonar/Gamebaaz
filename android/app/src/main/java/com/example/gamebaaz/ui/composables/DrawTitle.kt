package com.example.gamebaaz.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawTitle(
    text: String,
    modifier: Modifier? = null,
    color: Color = MaterialTheme.colors.onPrimary,
    alpha: Float = 1f,
    fontSize: TextUnit = 16.sp,
    style: TextStyle? = null
) {
    Text(
        text = text,
        style = style?.copy(
                color = color.copy(alpha = alpha),
                fontSize = fontSize
            )
            ?: MaterialTheme.typography.body2
            .copy(
                color = color.copy(alpha = alpha),
                fontSize = fontSize
            ),
        textAlign = TextAlign.Justify,
        modifier = modifier ?: Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun DrawSearchTitle(
    text: String,
    modifier: Modifier = Modifier,
    showLoader: Boolean = false
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
        Spacer(modifier = Modifier.width(width = 16.dp))
        if (showLoader){
            DrawLoader()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}