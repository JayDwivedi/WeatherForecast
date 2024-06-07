package com.jay.weatherforecastapp.data

import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.data.remote.WeatherApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {
    suspend fun getWeather(city: String): WeatherResponse = apiService.getWeather(city)
}
