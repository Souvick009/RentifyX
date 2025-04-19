package com.example.rentifyx.navigation

sealed class Routes(val route: String) {
    object DeciderScreen : Routes("DeciderScreen")
    object WelcomeScreen : Routes("WelcomeScreen")
    object HomeScreen : Routes("HomeScreen")
    object UserDetailsScreen : Routes("UserDetailsScreen")
    object SearchScreen : Routes("SearchScreen")

    object MainScreen : Routes("MainScreen")
    object ListScreen : Routes("ListScreen")
    object ListItemScreen : Routes("ListItemScreen")
    object WishListScreen : Routes("WishListScreen")
    object SettingsScreen : Routes("SettingsScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}