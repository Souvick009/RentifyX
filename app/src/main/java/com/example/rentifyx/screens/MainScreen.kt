package com.example.rentifyx.screens

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rentifyx.navigation.Routes

@Preview
@Composable
private fun check() {
    MainScreen(NavHostController(LocalContext.current))
}
@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    val items = listOf(
        Triple(Routes.HomeScreen.route, Icons.Outlined.Home, "Home"),
        Triple(Routes.ListScreen.route, Icons.Outlined.Menu, "Listings"),
        Triple(Routes.ListItemScreen.route, Icons.Outlined.AddCircle, "List"),
        Triple(Routes.WishListScreen.route, Icons.Outlined.ShoppingCart, "Saved"),
        Triple(Routes.SettingsScreen.route, Icons.Outlined.Settings, "Settings")
    )

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.height(60.dp)) {
                val currentRoute =
                    bottomNavController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { (screenRoute, icon, screenName) ->
                    val selected = currentRoute == screenRoute
                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(6.dp)
                                        .width(30.dp)
                                        .background(
                                            if (selected) MaterialTheme.colorScheme.primary
                                            else Color.Transparent,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                        ),
                        label = {
                            Text(
                                screenName,
                                maxLines = 1,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        },
                        onClick = {
                            if (currentRoute != screenRoute) {
                                bottomNavController.navigate(screenRoute) {
                                    launchSingleTop = true
                                    popUpTo(bottomNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }) { innerPadding ->

        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = Routes.HomeScreen.route) {
                HomeScreen(navController, bottomNavController)
            }
            composable(route = Routes.ListScreen.route) {
                ListScreen(bottomNavController)
            }
            composable(route = Routes.ListItemScreen.route) {
                ListItemScreen(bottomNavController)
            }
            composable(route = Routes.WishListScreen.route) {
                WishListScreen(bottomNavController)
            }
            composable(route = Routes.SettingsScreen.route) {
                SettingsScreen(bottomNavController)
            }
        }
    }
}
