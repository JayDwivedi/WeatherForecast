package com.jay.weatherforecastapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "weather") {
        composable("weather") {
            WeatherScreen()
        }
    }
}
