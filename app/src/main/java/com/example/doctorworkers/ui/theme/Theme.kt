package com.example.doctorworkers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkPallete = darkColors(
    primary = Orange200,
    primaryVariant = Orange700,
    background = Gray700,
    surface = Gray500
)

private val lightPallete = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Orange200,
    secondaryVariant = Orange700,
    background = Color.White,
    surface = Color.White

)

@Composable
fun MaterialThemeDoctor(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUi = rememberSystemUiController()

    val colors = if (isDark) {
        systemUi.setStatusBarColor(Orange700)
        darkPallete
    } else {
        systemUi.setStatusBarColor(Blue700)
        lightPallete
    }

    MaterialTheme(colors, content = content)
}