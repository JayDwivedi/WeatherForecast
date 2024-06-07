package com.jay.weatherforecastapp.data.model

data class WeatherResponse(
    val temperature: String,
    val wind: String,
    val description: String,
    val forecast: List<Forecast>
)

data class Forecast(
    val day: Int,
    val temperature: String,
    val wind: String
)

