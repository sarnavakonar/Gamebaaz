package com.example.gamebaaz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.model.response.GamesResponse
import com.example.gamebaaz.ui.composables.*
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                GamesList(requireActivity().window, viewModel)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllGames()
    }

    @Composable
    fun GamesList(window: Window, viewModel: GameViewModel) {

        val data: GamesResponse? = null
        val gameData = remember { mutableStateOf( data ) }

        viewModel.getGames().observe(viewLifecycleOwner , {
            when (it.status) {
                Status.SUCCESS -> {
                    gameData.value = it.data
                }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
            }
        })

        GamebaazTheme(window = window) {
            // A surface container using the 'background' color from the theme
            Surface(
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {

                if(gameData.value == null){
                    DrawLoaderBox()
                }
                else{
                    LazyColumn {

                        item {
                            DrawDevelopers(
                                window = window,
                                viewModel = viewModel,
                                navController = findNavController(),
                                developers = gameData.value?.developers!!
                            )
                            Spacer(modifier = Modifier.height(height = 8.dp))
                        }

                        itemsIndexed(gameData.value!!.categories) { index, category ->

                            Spacer(modifier = Modifier.height(height = 8.dp))

                            DrawTitle(text = category.name.uppercase())

                            DrawGames(
                                window = window,
                                viewModel = viewModel,
                                navController = findNavController(),
                                games= category.games
                            )
                        }
                    }
                }
            }
        }
    }

}