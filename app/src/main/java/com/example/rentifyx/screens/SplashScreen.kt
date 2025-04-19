package com.example.rentifyx.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rentifyx.R
import com.example.rentifyx.navigation.Routes
import com.example.rentifyx.states.AuthState
import com.example.rentifyx.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val authState = authViewModel.authState.collectAsState().value

    LaunchedEffect(authState) {
        // Intro: Expand/Inflate
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            )
        }

        // Intro: Increasing Opacity
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            )
        }


        delay(1200L) // Small pause

        // Outro: Shrink
        launch {
            scale.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 600)
            )
        }

        // Outro: Fade
        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 600)
            )
        }

        delay(500L) // Wait for outro to finish

        Log.d("hmm", "authState: $authState")
        when (authState) {
            // if user is not Authenticated

            AuthState.Unauthenticated -> {
                navController.navigate(Routes.WelcomeScreen.route) {
                    popUpTo(Routes.DeciderScreen.route) {
                        inclusive = true
                    }
                }
            }

            // if user is either Authenticated or a Guest
            else -> {
                navController.navigate(Routes.MainScreen.route) {
                    popUpTo(Routes.DeciderScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Splash Screen Logo
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }

}