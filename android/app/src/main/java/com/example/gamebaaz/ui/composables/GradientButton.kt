package com.example.gamebaaz.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            DrawTitle(
                text = text,
                color = textColor
            )
        }
    }
}