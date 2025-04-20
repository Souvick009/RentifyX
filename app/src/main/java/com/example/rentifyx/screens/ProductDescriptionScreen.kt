package com.example.rentifyx.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.rentifyx.reusablecomposable.BaseScreen

@Composable
fun ProductDescriptionScreen() {
    BaseScreen(
        modifier = Modifier,
        isAppBarNeeded = true,
        appBarTitle = "Product Info",
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(it)
            )
            {
                Text("Product Description")
            }
        }
    )
}