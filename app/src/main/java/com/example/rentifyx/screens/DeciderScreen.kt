package com.example.rentifyx.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.rentifyx.navigation.Routes
import com.example.rentifyx.states.AuthState
import com.example.rentifyx.viewmodel.AuthViewModel

@Composable
fun DeciderScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.collectAsState().value
    LaunchedEffect(authState) {
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
                navController.navigate(Routes.HomeScreen.route) {
                    popUpTo(Routes.DeciderScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}