package com.example.rentifyx.navigation

import android.app.Application
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rentifyx.repository.AuthRepository
import com.example.rentifyx.screens.MainScreen
import com.example.rentifyx.screens.ProductDescriptionScreen
import com.example.rentifyx.screens.SearchScreen
import com.example.rentifyx.screens.SplashScreen
import com.example.rentifyx.screens.UserDetailsScreen
import com.example.rentifyx.screens.WelcomeScreen
import com.example.rentifyx.viewmodel.AuthViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authViewModel: AndroidViewModel = AuthViewModel(
        authRepository,
        LocalContext.current.applicationContext as Application
    )

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
            SplashScreen(navController, authViewModel as AuthViewModel)
        }
        composable(route = Routes.WelcomeScreen.route) {
            WelcomeScreen(navController, authViewModel as AuthViewModel)
        }

        composable(route = Routes.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = Routes.UserDetailsScreen.route) {
            UserDetailsScreen(appNavController = navController, authViewModel as AuthViewModel)
        }
        composable(route = Routes.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = Routes.ProductDescriptionScreen.route) {
            ProductDescriptionScreen()
        }
    }
}

