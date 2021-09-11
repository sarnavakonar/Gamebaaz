package com.example.gamebaaz.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Game
import com.example.gamebaaz.ui.composables.*
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.ui.theme.UnselectedIconColor
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import com.google.accompanist.glide.rememberGlidePainter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGameFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DrawSearchUI(requireActivity().window, viewModel)
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun DrawSearchUI(window: Window, viewModel: GameViewModel) {

        val focusManager = LocalFocusManager.current
        val resultText = remember { mutableStateOf("TRENDING NOW") }
        val searchText = remember { mutableStateOf("") }
        val games = remember { mutableStateOf( mutableListOf<Game>() ) }
        val showLoader = remember { mutableStateOf(false) }
        val trendingGames = mutableListOf<Game>()

        viewModel.getSearchedText().observe(viewLifecycleOwner, {
            searchText.value = it
        })
        viewModel.getGames().observe(viewLifecycleOwner , {
            when (it.status) {
                Status.SUCCESS -> {
                    val trending = it.data?.categories?.filter {
                        it.name == "Trending"
                    }
                    if(!trending.isNullOrEmpty()){
                        trendingGames.addAll(trending[0].games)
                        games.value = trendingGames
                    }
                }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
            }
        })
        viewModel.getSearchedGames().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoader.value = false
                    games.value = mutableListOf()
                    if(!it.data?.games.isNullOrEmpty()){
                        games.value = it.data?.games?.toMutableList()!!
                        resultText.value = "TOP RESULTS"
                    }
                    else
                        resultText.value = "SHIT! GAME HASN'T BEEN INVENTED YET :/"
                }
                Status.ERROR -> {
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

                    TextField(
                        value = searchText.value,
                        onValueChange = {
                            viewModel.setSearchedText(it)
                            if(it.isNotBlank()) {
                                showLoader.value = true
                                viewModel.searchGames()
                            }
                            else {
                                showLoader.value = false
                                viewModel.cancelSearch()
                                games.value = trendingGames
                                resultText.value = "TRENDING NOW"
                            }
                        },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Search Game",
                                style = MaterialTheme.typography.button.copy(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                                )
                            )},
                        textStyle = MaterialTheme.typography.button,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_search_24),
                                contentDescription = null,
                                tint = UnselectedIconColor,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                            )},
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(
                                width = 1.dp,
                                color = Purple200,
                                shape = MaterialTheme.shapes.medium
                            ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )

                    if (resultText.value.contains("SHIT")){
                        DrawSearchTitle(
                            text = resultText.value,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            showLoader = showLoader.value
                        )
                    }
                    else{
                        DrawSearchTitle(
                            text = resultText.value,
                            showLoader = showLoader.value
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    LazyColumn {

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