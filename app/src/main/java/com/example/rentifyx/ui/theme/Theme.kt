package com.example.rentifyx.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = WelcomeScreenColor,
    background = backgroundColor,
    secondaryContainer = appBarDividerColor,
    surface = surfaceColor
)

@Composable
fun RentifyXTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}