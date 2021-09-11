package com.example.gamebaaz.ui.theme

import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Color.DarkGray,
    onSurface = Color.White,
    onBackground = Color.Black,
    onPrimary = DARKGRAY

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Color.White,
    onSurface = Color.Black,
    onBackground = Color.White,
    onPrimary = DARKWHITE
)

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun GamebaazTheme(window: Window, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    if (!darkTheme)
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        DarkTypography
    } else {
        LightTypography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}