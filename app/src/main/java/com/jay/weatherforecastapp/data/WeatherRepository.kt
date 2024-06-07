package com.jay.weatherforecastapp.data

import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.data.remote.WeatherApiService
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {
    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWeather(city)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
