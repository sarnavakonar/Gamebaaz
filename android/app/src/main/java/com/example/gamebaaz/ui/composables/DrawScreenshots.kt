package com.example.gamebaaz.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.gamebaaz.R
import com.example.gamebaaz.view.GameViewModel
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun DrawScreenshots(
    viewModel: GameViewModel,
    screenshots: List<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, end = 8.dp)
                .weight(weight = 0.5f)
                .height(height = 104.dp)
                .graphicsLayer {
                    shadowElevation = 8.dp.toPx()
                    shape = RoundedCornerShape(12.dp)
                    clip = true
                }
        ) {
            Image(
                painter = rememberGlidePainter(
                    request = screenshots[0],
                    previewPlaceholder = R.drawable.fifa
                ),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }

        if (screenshots.size > 1){
            Card(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 16.dp, end = 16.dp)
                    .weight(weight = 0.5f)
                    .height(height = 104.dp)
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = RoundedCornerShape(12.dp)
                        clip = true
                    }
            ) {
                Image(
                    painter = rememberGlidePainter(
                        request = screenshots[1],
                        previewPlaceholder = R.drawable.fifa
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
        else {
            Spacer(modifier = Modifier.weight(weight = 0.5f))
        }
    }
}