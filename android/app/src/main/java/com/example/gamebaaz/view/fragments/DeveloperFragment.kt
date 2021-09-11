package com.example.gamebaaz.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Developer
import com.example.gamebaaz.model.Game
import com.example.gamebaaz.ui.composables.DrawGamesSearch
import com.example.gamebaaz.ui.composables.DrawLoaderBox
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import com.google.accompanist.glide.rememberGlidePainter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperFragment: Fragment() {

    companion object{
        const val DEVELOPER = "developer"
    }

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DrawGamesListUI(window = requireActivity().window, viewModel = viewModel)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dev = arguments?.get(DEVELOPER) as Developer
        viewModel.setDeveloper(dev = dev)
        Log.e("lala", "dev name from arg is ${dev.name}")

        viewModel.fetchGamesByDev()
    }

    @Composable
    private fun DrawGamesListUI(window: Window, viewModel: GameViewModel) {

        val context = LocalContext.current
        val games = remember { mutableStateOf( mutableListOf<Game>() ) }
        val showLoader = remember { mutableStateOf(true) }

        val developer =  viewModel.getDeveloper()

        viewModel.getGamesByDev().observe(viewLifecycleOwner, {
            it?.let {
                val gamesListResponse = it[developer?.id]
                when(gamesListResponse?.status){
                    Status.SUCCESS -> {
                        showLoader.value = false
                        if(!gamesListResponse.data?.games.isNullOrEmpty()){
                            games.value = gamesListResponse.data?.games?.toMutableList()!!
                        }
                    }
                    Status.ERROR -> {
                        showLoader.value = false
                    }
                    Status.LOADING -> {
                    }
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

                LazyColumn {

                    item {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = rememberGlidePainter(
                                    request = developer?.logo,
                                    previewPlaceholder = R.drawable.ea
                                ),
                                contentDescription = "",
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

                            Spacer(modifier = Modifier.width(width = 16.dp))

                            Text(
                                text = "${developer?.name}",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }

                    item {
                        Text(
                            text = "${developer?.about}",
                            style = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.65f),
                                textAlign = TextAlign.Justify,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "Founded: ${developer?.founded}",
                            style = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.65f),
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.twitter),
                                contentDescription = "Twitter",
                                modifier = Modifier
                                    .size(size = 24.dp)
                                    .clickable {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(developer?.twitter))
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.instagram),
                                contentDescription = "Instagram",
                                modifier = Modifier
                                    .size(size = 24.dp)
                                    .clickable {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(developer?.insta))
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = "Facebook",
                                modifier = Modifier
                                    .size(size = 24.dp)
                                    .clickable {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(developer?.fb))
                                    )
                                }
                            )
                        }
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 1.dp)
                                .padding(start = 16.dp, end = 16.dp)
                                .background(color = Purple200)
                        )
                    }

                    if(showLoader.value) {
                        item {
                            DrawLoaderBox()
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(height = 16.dp))
                    }

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