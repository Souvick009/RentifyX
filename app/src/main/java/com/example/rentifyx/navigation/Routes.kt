package com.example.rentifyx.navigation

sealed class Routes(val route: String) {
    object WelcomeScreen : Routes("WelcomeScreen")
    object TestScreen : Routes("TestScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }

    }
}