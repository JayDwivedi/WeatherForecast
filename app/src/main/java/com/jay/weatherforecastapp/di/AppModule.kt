package com.jay.weatherforecastapp.di

import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.remote.WeatherApiService
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository {
        return WeatherRepository(apiService)
    }

    @Provides
    fun provideWeatherViewModel(repository: WeatherRepository)= WeatherViewModel(repository)

}