package com.example.rentifyx.navigation

import android.app.Application
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rentifyx.repository.AuthRepository
import com.example.rentifyx.screens.DeciderScreen
import com.example.rentifyx.screens.HomeScreen
import com.example.rentifyx.screens.SearchScreen
import com.example.rentifyx.screens.UserDetailsScreen
import com.example.rentifyx.screens.WelcomeScreen
import com.example.rentifyx.states.AuthState
import com.example.rentifyx.viewmodel.AuthViewModel

@Composable
fun NavController() {

    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authViewModel: AndroidViewModel = AuthViewModel(
        authRepository,
        LocalContext.current.applicationContext as Application
    )

//    val authState = (authViewModel as AuthViewModel).authState.collectAsState()
//    val startDestination = when  {
//        authState.value is AuthState.Authenticated && authViewModel.hasCompletedProfileAndSignedIn -> Routes.HomeScreen.route
//        else -> Routes.WelcomeScreen.route
//    }

    Log.d("hmm", "1")

    NavHost(
        navController = navController,
        startDestination = Routes.DeciderScreen.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable(
            route = Routes.DeciderScreen.route,
//            exitTransition = { fadeOut(animationSpec = tween(1000, easing = FastOutSlowInEasing)) }
        ) {
            DeciderScreen(navController, authViewModel as AuthViewModel)
        }
        composable(route = Routes.WelcomeScreen.route) {
            WelcomeScreen(navController, authViewModel as AuthViewModel)
        }

        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Routes.UserDetailsScreen.route) {
            UserDetailsScreen(navController = navController, authViewModel as AuthViewModel)
        }
        composable(route = Routes.SearchScreen.route) {
            SearchScreen(navController)
        }
    }
}

