package com.example.rentifyx.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rentifyx.screens.HomeScreen
import com.example.rentifyx.screens.SearchScreen
import com.example.rentifyx.screens.UserDetailsScreen
import com.example.rentifyx.screens.WelcomeScreen

@Composable
fun NavController() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WelcomeScreen.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable(route = Routes.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Routes.UserDetailsScreen.route) {
            UserDetailsScreen()
        }
        composable(route = Routes.SearchScreen.route) {
            SearchScreen(navController)
        }

    }
}

