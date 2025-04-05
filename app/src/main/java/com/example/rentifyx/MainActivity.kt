package com.example.rentifyx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rentifyx.screens.WelcomeScreenWithConstraint
import com.example.rentifyx.ui.theme.RentifyXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentifyXTheme {
                WelcomeScreenWithConstraint()
//                WelcomeScreen()
            }
        }
    }
}

