package com.example.gamebaaz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Game
import com.example.gamebaaz.ui.composables.DrawGamesSearch
import com.example.gamebaaz.ui.composables.DrawLoaderBox
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DrawProfileUI(window = requireActivity().window, viewModel = viewModel)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchFavGames()
    }

    @Composable
    private fun DrawProfileUI(window: Window, viewModel: GameViewModel) {

        val interactionSource = remember { MutableInteractionSource() }
        val games = remember { mutableStateOf( mutableListOf<Game>() ) }
        val selected = remember { mutableStateOf(3) }
        val showLoader = remember { mutableStateOf(true) }

        viewModel.getFavGames().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    showLoader.value = false
                    it.data?.games?.toMutableList()?.let {
                        games.value = it
                    }
                }
                Status.ERROR -> {
                    showLoader.value = false
                }
                Status.LOADING -> {
                }
            }
        })

        GamebaazTheme(window = window) {
            Surface(
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {

                Column {

                    Card(
                        modifier = Modifier.padding(bottom = 0.dp),
                        shape = MaterialTheme.shapes.medium,
                        backgroundColor = MaterialTheme.colors.onSurface,
                        elevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .shadow(
                                    elevation = 0.dp,
                                    shape = RoundedCornerShape(
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(height = 16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.dp),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, Purple200, CircleShape)
                            )
                            Spacer(modifier = Modifier.height(height = 16.dp))
                            Text(
                                text = viewModel.getUsername().toString(),
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.height(height = 24.dp))
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            selected.value = 1
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "0",
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(modifier = Modifier.height(height = 2.dp))
                                    Text(
                                        text = "Followers",
                                        style = MaterialTheme.typography.body1.copy(
                                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                                            fontSize = 14.sp
                                        )
                                    )
                                    if(selected.value == 1){
                                        Spacer(modifier = Modifier.height(height = 4.dp))
                                        Spacer(
                                            modifier = Modifier
                                                .size(width = 4.dp, height = 4.dp)
                                                .clip(CircleShape)
                                                .background(color = Purple200)
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            selected.value = 2
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "0",
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(modifier = Modifier.height(height = 2.dp))
                                    Text(
                                        text = "Following",
                                        style = MaterialTheme.typography.body1.copy(
                                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                                            fontSize = 14.sp
                                        )
                                    )
                                    if(selected.value == 2){
                                        Spacer(modifier = Modifier.height(height = 4.dp))
                                        Spacer(
                                            modifier = Modifier
                                                .size(width = 4.dp, height = 4.dp)
                                                .clip(CircleShape)
                                                .background(color = Purple200)
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            selected.value = 3
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${games.value.size}",
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(modifier = Modifier.height(height = 2.dp))
                                    Text(
                                        text = "Favourites",
                                        style = MaterialTheme.typography.body1.copy(
                                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                                            fontSize = 14.sp
                                        )
                                    )
                                    if(selected.value == 3){
                                        Spacer(modifier = Modifier.height(height = 4.dp))
                                        Spacer(
                                            modifier = Modifier
                                                .size(width = 4.dp, height = 4.dp)
                                                .clip(CircleShape)
                                                .background(color = Purple200)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if(showLoader.value) {
                        DrawLoaderBox(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    LazyColumn {

                        item {
                            Spacer(modifier = Modifier.padding(top = 24.dp))
                        }

                        when(selected.value){
                            1 -> {

                            }
                            2 -> {

                            }
                            3 -> {

                                if(!games.value.isNullOrEmpty()) {

                                    //0-> 0,1;;1-> 2,3;;2->4,5;;3-> 6,7;;4-> 8,9;;5-> 10,11
                                    items(games.value.windowed(2, 2, true).size) { item ->
                                        val index = 2*item
                                        val subList = mutableListOf<Game>()
                                        if(games.value.size > index+1) {
                                            subList.add(games.value[index])
                                            subList.add(games.value[index+1])
                                        }
                                        else{
                                            subList.add(games.value[index])
                                        }
                                        DrawGamesSearch(
                                            window = window,
                                            viewModel = viewModel,
                                            navController = findNavController(),
                                            games = subList
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

}