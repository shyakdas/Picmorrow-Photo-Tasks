package com.picmorrow.navigation

sealed class AppDestination(val route: String) {
    data object Welcome : AppDestination("welcome")
    data object Home : AppDestination("home")
}
