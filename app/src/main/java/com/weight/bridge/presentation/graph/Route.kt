package com.weight.bridge.presentation.graph

sealed class Route(val route: String) {

    object ListScreen : Route("ListScreen")
    object AddScreen : Route("AddScreen")
    object SplashScreen : Route("SplashScreen")

    object MainNav: Route("MainNav")
}