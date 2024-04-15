package com.android.clix.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.clix.presentations.screens.detail.DetailScreen
import com.android.clix.presentations.screens.home.HomeScreen
import com.android.clix.presentations.screens.search.SearchScreen

@ExperimentalFoundationApi
@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navHostController)
        }

        composable(
            route = Screen.Search.route
        ) {
            SearchScreen(navController = navHostController)
        }

        composable(
            route = Screen.Detail.route
        ) {
            DetailScreen(navController = navHostController)
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}