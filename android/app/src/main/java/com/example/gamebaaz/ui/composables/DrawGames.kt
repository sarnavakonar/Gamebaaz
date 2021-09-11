package com.example.gamebaaz.ui.composables

import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Game
import com.example.gamebaaz.view.GameViewModel
import com.example.gamebaaz.view.fragments.GameDetailFragment
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun DrawGames(
    window: Window,
    viewModel: GameViewModel,
    navController: NavController?,
    games: List<Game>
) {
    val gameListState = rememberLazyListState()

    LazyRow(
        state = gameListState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            games
        ) { index, game ->
            Card(
                modifier = Modifier
                    .padding(
                        start = 16.dp, top = 16.dp, bottom = 16.dp,
                        end = if (index == games.size - 1)
                            16.dp
                        else
                            0.dp
                    )
                    .size(width = 190.dp, height = 114.dp)
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = RoundedCornerShape(12.dp)
                        clip = true
                    }
                    .clickable {
                        navController?.navigate(
                            R.id.action_homeFragment_to_gameDetailFragment,
                            bundleOf(GameDetailFragment.GAME_ID to game.id)
                        )
                    }
            ) {
                Image(
                    painter = rememberGlidePainter(
                        request = game.image,
                        previewPlaceholder = R.drawable.fifa
                    ),
                    contentDescription = game.name,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}

@Composable
fun DrawGamesSearch(
    window: Window,
    viewModel: GameViewModel,
    navController: NavController?,
    games: List<Game>
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
                .clickable {
                    if (navController?.currentDestination?.id == R.id.searchGameFragment)
                        navController.navigate(
                            R.id.action_searchGameFragment_to_gameDetailFragment,
                            bundleOf(GameDetailFragment.GAME_ID to games[0].id)
                        )
                    else if (navController?.currentDestination?.id == R.id.developerFragment)
                        navController.navigate(
                            R.id.action_developerFragment_to_gameDetailFragment,
                            bundleOf(GameDetailFragment.GAME_ID to games[0].id)
                        )
                    else if (navController?.currentDestination?.id == R.id.profileFragment)
                        navController.navigate(
                            R.id.action_profileFragment_to_gameDetailFragment,
                            bundleOf(GameDetailFragment.GAME_ID to games[0].id)
                        )
                }
        ) {
            Image(
                painter = rememberGlidePainter(
                    request = games[0].image,
                    previewPlaceholder = R.drawable.fifa
                ),
                contentDescription = games[0].name,
                contentScale = ContentScale.FillBounds,
            )
        }

        if (games.size > 1){
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
                    .clickable {
                        if (navController?.currentDestination?.id == R.id.searchGameFragment)
                            navController.navigate(
                                R.id.action_searchGameFragment_to_gameDetailFragment,
                                bundleOf(GameDetailFragment.GAME_ID to games[1].id)
                            )
                        else if (navController?.currentDestination?.id == R.id.developerFragment)
                            navController.navigate(
                                R.id.action_developerFragment_to_gameDetailFragment,
                                bundleOf(GameDetailFragment.GAME_ID to games[1].id)
                            )
                        else if (navController?.currentDestination?.id == R.id.profileFragment)
                            navController.navigate(
                                R.id.action_profileFragment_to_gameDetailFragment,
                                bundleOf(GameDetailFragment.GAME_ID to games[1].id)
                            )
                    }
            ) {
                Image(
                    painter = rememberGlidePainter(
                        request = games[1].image,
                        previewPlaceholder = R.drawable.fifa
                    ),
                    contentDescription = games[1].name,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
        else {
            Spacer(modifier = Modifier.weight(weight = 0.5f))
        }
    }
}