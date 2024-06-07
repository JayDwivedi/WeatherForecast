package com.jay.weatherforecastapp.data.remote

import com.jay.weatherforecastapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {
    @GET("weather/{city}")
    suspend fun getWeather(@Path("city") city: String): WeatherResponse
}