package com.jay.weatherforecastapp.data

import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.util.Result

interface WeatherRepository {
    suspend fun getWeather(city: String): Result<WeatherResponse>
}