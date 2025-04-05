package com.example.rentifyx.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val LightColorScheme = lightColorScheme(

)

@Suppress("SpellCheckingInspection")
@Composable
fun RentifyXTheme(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colorScheme = LightColorScheme
    SideEffect {
        systemUiController.setStatusBarColor(
            color = WelcomeScreenColor,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = WelcomeScreenColor,
            navigationBarContrastEnforced = false
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}