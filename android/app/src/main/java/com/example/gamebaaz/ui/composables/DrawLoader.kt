package com.example.gamebaaz.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gamebaaz.ui.theme.Purple200

@Composable
fun DrawLoaderBox(
    modifier: Modifier? = null,
    contentAlignment: Alignment = Alignment.TopCenter
){
    Box(
        modifier = modifier ?: Modifier.padding(16.dp),
        contentAlignment = contentAlignment
    ) {
        DrawLoader()
    }
}

@Composable
fun DrawLoader(){
    CircularProgressIndicator(
        color = Purple200,
        strokeWidth = 1.5.dp,
        modifier = Modifier
            .size(16.dp)
    )
}