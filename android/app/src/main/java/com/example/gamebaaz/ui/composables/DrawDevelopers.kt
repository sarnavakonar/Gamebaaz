package com.example.gamebaaz.ui.composables

import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Developer
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.view.GameViewModel
import com.example.gamebaaz.view.fragments.DeveloperFragment
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun DrawDevelopers(
    window: Window,
    viewModel: GameViewModel,
    navController: NavController,
    developers: List<Developer>
) {
    val devListState = rememberLazyListState()
    val interactionSource = remember { MutableInteractionSource() }

    LazyRow(
        state = devListState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            developers
        ){ index, dev ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if(index == developers.size-1){
                    Modifier
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                            end = 16.dp
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
//                            scope.launch {
//                                devListState.animateScrollToItem(index = index)
//                            }
                            navController.navigate(
                                R.id.action_homeFragment_to_developerFragment,
                                bundleOf(DeveloperFragment.DEVELOPER to dev)
                            )
                        }
                }
                else{
                    Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(
                                R.id.action_homeFragment_to_developerFragment,
                                bundleOf(DeveloperFragment.DEVELOPER to dev)
                            )
                        }
                }
            ) {
                Image(
                    painter = rememberGlidePainter(
                        request = dev.logo,
                        previewPlaceholder = R.drawable.ea
                    ),
                    contentDescription = "Account",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .border(2.dp, Purple200, CircleShape)
                        .border(
                            4.dp,
                            MaterialTheme.colors.onSurface,
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(
                    text = dev.name,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.surface,
                        fontSize = 14.sp
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(68.dp)
                )
            }
        }
    }
}