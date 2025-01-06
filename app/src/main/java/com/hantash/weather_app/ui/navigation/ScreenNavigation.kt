package com.hantash.weather_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hantash.weather_app.ui.screen.AboutScreen
import com.hantash.weather_app.ui.screen.FavoriteScreen
import com.hantash.weather_app.ui.screen.MainScreen
import com.hantash.weather_app.ui.screen.SearchScreen
import com.hantash.weather_app.ui.screen.SettingsScreen
import com.hantash.weather_app.ui.screen.SplashScreen

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val defaultScreen = EnumScreen.SPLASH_SCREEN.name

    NavHost(navController = navController, startDestination = defaultScreen) {

        composable(defaultScreen) {
            SplashScreen(navController)
        }

        composable(route = EnumScreen.MAIN_SCREEN.name) {
            MainScreen(navController)
        }

        composable(route = EnumScreen.ABOUT_SCREEN.name) {
            AboutScreen(navController)
        }

        composable(route = EnumScreen.FAVORITE_SCREEN.name) {
            FavoriteScreen(navController)
        }

        composable(route = EnumScreen.SEARCH_SCREEN.name) {
            SearchScreen(navController)
        }

        composable(route = EnumScreen.SETTINGS_SCREEN.name) {
            SettingsScreen(navController)
        }
    }

}