package com.example.gamebaaz.view.fragments

import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.R
import com.example.gamebaaz.model.Game
import com.example.gamebaaz.ui.composables.*
import com.example.gamebaaz.ui.theme.DARKYELLOW
import com.example.gamebaaz.ui.theme.FAVRED
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import com.google.accompanist.glide.rememberGlidePainter
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment: Fragment() {

    companion object{
        const val GAME_ID = "gameId"
    }

    private val viewModel: GameViewModel by activityViewModels()

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Game(requireActivity().window, viewModel = viewModel)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gameId = arguments?.getInt(GAME_ID)
        viewModel.setGameId(gameId!!)
        Log.e("lala", "game id from arg is $gameId")

        viewModel.fetchGameDetails()
    }

    @ExperimentalAnimationApi
    @Composable
    fun Game(window: Window, viewModel: GameViewModel) {

        val context = LocalContext.current
        val videoView = remember { mutableStateOf(Unit) }
        val play = remember { mutableStateOf(true) }
        val playImage = remember { mutableStateOf(R.drawable.play) }
        val playAlpha = remember { mutableStateOf(0f) }
        val videoLoaded = remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val game = remember { mutableStateOf(mutableListOf<Game>()) }

        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
        val lifecycle = lifecycleOwner.value.lifecycle

        // Do not recreate the player everytime this Composable commits
        val exoPlayer = remember {
            SimpleExoPlayer.Builder(context).build()
        }

        val alpha: Float by animateFloatAsState(
            targetValue = if(playAlpha.value == 1f) 1f else 0f,
            animationSpec = tween(
                durationMillis = if(playAlpha.value == 1f) 0 else 2250,
                easing = LinearOutSlowInEasing
            ),
            finishedListener = {
                if(playAlpha.value == 1f) {
                    playAlpha.value = .99f
                }
            }
        )

        val observer = LifecycleEventObserver { owner, event ->
            if(event == Lifecycle.Event.ON_PAUSE){
                exoPlayer.pause()
            }
            else if(event == Lifecycle.Event.ON_RESUME && play.value){
                exoPlayer.play()
            }
        }

        val gameId = viewModel.getGamedId()

        viewModel.getGameDetails().observe(viewLifecycleOwner, {
            it?.let {
                val gameData = it[gameId]
                when(gameData?.status){
                    Status.SUCCESS -> {
                        gameData.data?.gamesData?.let {
                            game.value = mutableListOf(it)
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })

        GamebaazTheme(window = window) {
            Surface(
                color = MaterialTheme.colors.background
            ) {

                if(game.value.isNotEmpty()) {

                    val gameData = game.value[0]

                    LaunchedEffect(Unit){
                        scope.launch {
                            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context,
                                Util.getUserAgent(context, context.packageName))

                            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(
                                    Uri.parse(
                                        gameData.video//"https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"
                                    ))
                            exoPlayer.prepare(source)
                            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                            exoPlayer.addListener(object : Player.EventListener {
                                override fun onPlaybackStateChanged(state: Int) {
                                    if(state == Player.STATE_READY) {
                                        videoLoaded.value = true
                                    }
                                    super.onPlaybackStateChanged(state)
                                }
                            })
                        }
                    }

                    DisposableEffect(lifecycleOwner.value) {
                        lifecycle.addObserver(observer)
                        onDispose {
                            exoPlayer.stop(true)
                            exoPlayer.release()
                            scope.cancel()
                            lifecycle.removeObserver(observer)
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        item {
                            // Gateway to traditional Android Views
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                videoView.value = AndroidView(
                                    modifier = Modifier.clickable {
                                        play.value = !play.value
                                        if(play.value){
                                            playImage.value = R.drawable.play
                                        } else{
                                            playImage.value = R.drawable.pause
                                        }
                                        playAlpha.value = 1f
                                        exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                                    },
                                    factory = { context ->
                                        PlayerView(context).apply {
                                            hideController()
                                            useController = false
                                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

                                            player = exoPlayer
                                            (player as SimpleExoPlayer).playWhenReady = true

                                            val displayMetrics = DisplayMetrics()
                                            window.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
                                            val height = displayMetrics.heightPixels
                                            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/3)
                                        }
                                    }
                                )
                                AnimatedVisibility(
                                    visible = videoLoaded.value,
                                    enter = EnterTransition.None,
                                    exit = ExitTransition.None,
                                    initiallyVisible = true
                                ){
                                    Icon(
                                        painter = painterResource(playImage.value),
                                        contentDescription = "play",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(size = 56.dp)
                                            .alpha(alpha)
                                    )
                                }
                                AnimatedVisibility(
                                    visible = !videoLoaded.value,
                                    enter = EnterTransition.None,
                                    exit = ExitTransition.None,
                                    initiallyVisible = true
                                ){
                                    DrawLoader()
                                }
                            }
                        }

                        item {
                            DrawBody(gameData = gameData, navController = findNavController())
                        }

                        val screenshots = gameData.screenshots
                        screenshots?.let {
                            items(screenshots.windowed(2, 2, true).size) { item ->
                                val index = 2*item
                                val subList = mutableListOf<String>()
                                if(screenshots.size > index+1) {
                                    subList.add(screenshots[index])
                                    subList.add(screenshots[index+1])
                                }
                                else{
                                    subList.add(screenshots[index])
                                }
                                DrawScreenshots(
                                    viewModel = viewModel,
                                    screenshots = subList
                                )
                            }
                        }
                    }
                }
                else{
                    DrawLoaderBox()
                }
            }
        }
    }

    @Composable
    private fun DrawBody(gameData: Game, navController: NavController) {

        val interactionSource = remember { MutableInteractionSource() }
        val favImage = remember { mutableStateOf(
            if(gameData.isFavourite)
                R.drawable.ic_baseline_favorite_24
            else
                R.drawable.ic_baseline_favorite_border_24
        ) }

        val shouldAnimate = remember { mutableStateOf(false) }
        val size: Dp by animateDpAsState(
            targetValue = if(shouldAnimate.value) 28.dp else 24.dp,
            animationSpec = tween(
                durationMillis = 35,
                easing = LinearOutSlowInEasing
            ),
            finishedListener = {
                shouldAnimate.value = false
            }
        )

        //exoPlayer.volume = 0f
        Spacer(modifier = Modifier.height(height = 16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(height = 28.dp)
        ) {
            DrawTitle(text = gameData.name)
            Spacer(modifier = Modifier.weight(weight = 1f))
            Icon(
                painter = painterResource(id = favImage.value),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(size = size)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        shouldAnimate.value = true

                        if(favImage.value == R.drawable.ic_baseline_favorite_24) {
                            favImage.value = R.drawable.ic_baseline_favorite_border_24
                            viewModel.alterFavouriteGame(false)
                        }
                        else {
                            favImage.value = R.drawable.ic_baseline_favorite_24
                            viewModel.alterFavouriteGame(true)
                        }
                    },
                tint = FAVRED
            )
        }

        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrawTitle(
                text = gameData.categoty,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp),
                fontSize = 14.sp
            )
            DrawTitle(
                text = "${gameData.rating}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = 8.dp, end = 2.dp),
                fontSize = 14.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = null,
                tint = DARKYELLOW,
                modifier = Modifier.size(size = 20.dp)
            )
            if(gameData.trending == 1){
                Box(
                    modifier  = Modifier
                        .padding(start = 16.dp)
                        .background(
                            color = Purple200,
                            shape = RoundedCornerShape(2.dp)
                        )
                ){
                    DrawTitle(
                        text = "TRENDING",
                        modifier = Modifier
                            .padding(start = 6.dp, top = 1.5.dp, end = 6.dp, bottom = 1.5.dp),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        DrawTitle(
            text = "${gameData.description}",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            alpha = 0.65f
        )

        Spacer(modifier = Modifier.height(height = 4.dp))
        Text(
            text = "Release Date: ${gameData.released}",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.65f),
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        DrawTitle(
            text = "Developed by",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    navController.navigate(
                        R.id.action_gameDetailFragment_to_developerFragment,
                        bundleOf("developer" to gameData.developer)
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberGlidePainter(
                    request = gameData.developer?.logo,
                    previewPlaceholder = R.drawable.ea
                ),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Purple200, CircleShape)
                    .border(
                        3.dp,
                        MaterialTheme.colors.onSurface,
                        CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(width = 12.dp))
            Text(
                text = "${gameData.developer?.name}",
                style = MaterialTheme.typography.body1
            )
        }

        DrawTitle(
            text = "Screenshots",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 14.sp
        )

    }

}