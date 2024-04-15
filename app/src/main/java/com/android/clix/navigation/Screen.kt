package com.android.clix.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home")

    data object Detail: Screen(route = "detail")

    data object Search: Screen(route = "search")
}