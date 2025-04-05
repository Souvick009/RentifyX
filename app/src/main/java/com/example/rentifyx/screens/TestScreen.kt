package com.example.rentifyx.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text("Hello", modifier.statusBarsPadding())
    }

}