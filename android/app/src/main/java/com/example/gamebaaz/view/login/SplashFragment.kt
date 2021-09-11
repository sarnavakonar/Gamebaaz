package com.example.gamebaaz.view.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamebaaz.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.gamebaaz.R
import com.example.gamebaaz.view.GameViewModel
import com.example.gamebaaz.view.MainActivity

@AndroidEntryPoint
class SplashFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Splash()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        handler = Handler()
        handler.postDelayed({
            if(!viewModel.getLoginCredentials().isNullOrEmpty()) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            else
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    @Composable
    fun Splash() {

        val radius = remember { mutableStateOf(60f) }
        val DURATION = 500
        val shouldAnimate = remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val count = remember { mutableStateOf(0) }

        val radiusAlpha: Float by animateFloatAsState(
            targetValue = if(shouldAnimate.value) radius.value/6 else 2000f,
            animationSpec = tween(
                durationMillis = if(shouldAnimate.value) DURATION else 0,
                easing = LinearOutSlowInEasing
            )
        )
        val colorAlpha: Float by animateFloatAsState(
            targetValue = if(shouldAnimate.value) 1f else 0f,
            animationSpec = tween(
                durationMillis = if(shouldAnimate.value) DURATION else 0,
                easing = LinearOutSlowInEasing
            ),
            finishedListener = {}
        )

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val dimen = canvasWidth/28.8f
            radius.value = dimen + dimen/5

            drawPoints(
                points = listOf(
                    Offset(x = canvasWidth / 2 - 4*dimen, y = canvasHeight / 2 + 2*dimen),
                    Offset(x = canvasWidth / 2 - 3*dimen, y = canvasHeight / 2 - 2*dimen),

                    Offset(x = canvasWidth / 2 - 3*dimen + radius.value + radius.value/24, y = canvasHeight / 2 - 2*dimen - dimen/25),
                    Offset(x = canvasWidth / 2 + 3*dimen - radius.value - radius.value/24, y = canvasHeight / 2 - 2*dimen - dimen/25),

                    Offset(x = canvasWidth / 2 + 3*dimen, y = canvasHeight / 2 - 2*dimen),
                    Offset(x = canvasWidth / 2 + 4*dimen, y = canvasHeight / 2 + 2*dimen)
                ),
                pointMode = PointMode.Lines,
                brush = Brush.linearGradient(colors = listOf(Purple200, Purple200)),
                strokeWidth = dimen/5,
                cap = StrokeCap.Round
            )

            drawArc(
                color = Purple200,
                startAngle = -20f,
                sweepAngle = -165f,
                useCenter = false,
                style = Stroke(
                    width = dimen/5,
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(x = canvasWidth / 2 - 3*dimen, y = canvasHeight / 2 - 2*dimen - radius.value/4),
                size = Size(radius.value, radius.value/2)
            )

            drawArc(
                color = Purple200,
                startAngle = 0f,
                sweepAngle = -165f,
                useCenter = false,
                style = Stroke(
                    width = dimen/5,
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(x = canvasWidth / 2 + 3*dimen - radius.value, y = canvasHeight / 2 - 2*dimen - radius.value/4),
                size = Size(radius.value, radius.value/2)
            )

            drawArc(
                color = Purple200,
                startAngle = 180f,
                sweepAngle = -145f,
                useCenter = false,
                style = Stroke(
                    width = dimen/5,
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(x = canvasWidth / 2 - 4*dimen, y = canvasHeight / 2 + 2*dimen - radius.value/2),
                size = Size(radius.value, radius.value)
            )

            drawArc(
                color = Purple200,
                startAngle = 0f,
                sweepAngle = 145f,
                useCenter = false,
                style = Stroke(
                    width = dimen/5,
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(x = canvasWidth / 2 + 4*dimen - radius.value, y = canvasHeight / 2 + 2*dimen - radius.value/2),
                size = Size(radius.value, radius.value)
            )

            drawPoints(
                points = listOf(
                    Offset(x = canvasWidth / 2 - 4*dimen + radius.value - radius.value/9, y = canvasHeight / 2 + 2*dimen + radius.value/3),
                    Offset(x = canvasWidth / 2 - 4*dimen + radius.value + (dimen*4)/5, y = canvasHeight / 2 + radius.value),

                    Offset(x = canvasWidth / 2 - 4*dimen + 2*dimen, y = canvasHeight / 2 + radius.value),
                    Offset(x = canvasWidth / 2 + 4*dimen - 2*dimen, y = canvasHeight / 2 + radius.value),

                    Offset(x = canvasWidth / 2 + 4*dimen - radius.value - (dimen*4)/5, y = canvasHeight / 2 + radius.value),
                    Offset(x = canvasWidth / 2 + 4*dimen - radius.value + radius.value/9, y = canvasHeight / 2 + 2*dimen + radius.value/3)
                ),
                pointMode = PointMode.Lines,
                brush = Brush.linearGradient(colors = listOf(Purple200, Purple200)),
                strokeWidth = dimen/5,
                cap = StrokeCap.Round
            )

            drawCircle(
                color = Purple200,
                radius = radius.value/4f,
                center = Offset(x = canvasWidth / 2 + (dimen*4)/5, y = canvasHeight/2 + radius.value/4)
            )
            drawCircle(
                color = Purple200,
                radius = radius.value/2.5f,
                center = Offset(x = canvasWidth / 2 + (dimen*4)/5, y = canvasHeight/2 + radius.value/4),
                style = Stroke(
                    width = dimen/14.2857f,
                    cap = StrokeCap.Round
                )
            )

            drawPoints(
                points = listOf(
                    Offset(x = canvasWidth / 2 - dimen - dimen/10, y = canvasHeight/2 + radius.value/4),
                    Offset(x = canvasWidth / 2 - dimen/2, y = canvasHeight/2 + radius.value/4),

                    Offset(x = canvasWidth / 2 - (dimen*4)/5, y = canvasHeight / 2 ),
                    Offset(x = canvasWidth / 2 - (dimen*4)/5, y = canvasHeight / 2 + radius.value/2f)
                ),
                pointMode = PointMode.Lines,
                brush = Brush.linearGradient(colors = listOf(Purple200, Purple200)),
                strokeWidth = dimen/3.5714f,
                cap = StrokeCap.Round
            )

            drawCircle(
                color = Purple200,
                radius = radius.value/4f,
                center = Offset(x = canvasWidth / 2 - 2*dimen, y = canvasHeight/2 - radius.value/1.5f)
            )
            drawCircle(
                color = Purple200,
                radius = radius.value/2.5f,
                center = Offset(x = canvasWidth / 2 - 2*dimen, y = canvasHeight/2 - radius.value/1.5f),
                style = Stroke(
                    width = dimen/14.2857f,
                    cap = StrokeCap.Round
                )
            )

            drawRoundRect(
                color = Purple200,
                topLeft = Offset(x = canvasWidth / 2 - radius.value/4 , y = canvasHeight/2 - 1.125f*radius.value),
                size = Size(width = radius.value/2, height = radius.value/6),
                cornerRadius = CornerRadius(dimen/10),
                style = Stroke(
                    width = dimen/14.2857f,
                    cap = StrokeCap.Round
                )
            )
            drawRoundRect(
                color = Purple200,
                topLeft = Offset(x = canvasWidth / 2 - radius.value/8 , y = canvasHeight/2 - radius.value/1.25f),
                size = Size(width = radius.value/4, height = radius.value/8),
                cornerRadius = CornerRadius(dimen/10),
            )

            if (count.value > 0){
                shouldAnimate.value = true
                drawCircle(
                    color = CONTROLLER_GREEN.copy(alpha = colorAlpha),
                    radius = radiusAlpha,
                    center = Offset(x = canvasWidth / 2 + 2*dimen, y = canvasHeight/2 - radius.value/3)
                )
                drawCircle(
                    color = CONTROLLER_RED.copy(alpha = colorAlpha),
                    radius = radiusAlpha,
                    center = Offset(x = canvasWidth / 2 + 2*dimen + dimen/2, y = canvasHeight/2 - radius.value/1.5f)
                )
                drawCircle(
                    color = CONTROLLER_YELLOW.copy(alpha = colorAlpha),
                    radius = radiusAlpha,
                    center = Offset(x = canvasWidth / 2 + 2*dimen, y = canvasHeight/2 - radius.value)
                )
                drawCircle(
                    color = CONTROLLER_BLUE.copy(alpha = colorAlpha),
                    radius = radiusAlpha,
                    center = Offset(x = canvasWidth / 2 + 2*dimen - dimen/2, y = canvasHeight/2 - radius.value/1.5f)
                )
            }

            scope.launch {
                delay(500)
                count.value ++
            }

        }
    }
}