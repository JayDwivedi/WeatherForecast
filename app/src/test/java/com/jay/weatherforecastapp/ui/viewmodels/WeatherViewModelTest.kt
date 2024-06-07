package com.jay.weatherforecastapp.ui.viewmodels

import org.junit.Test


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherViewModel: WeatherViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        weatherRepository = mock()
        weatherViewModel = WeatherViewModel(weatherRepository)
    }

    @Test
    fun `fetchWeather sets loading and success state`() = runBlockingTest {
        val city = "San Francisco"
        val weatherResponse = WeatherResponse("20 °C", "10 km/h", "Clear", emptyList())
        whenever(weatherRepository.getWeather(city)).thenReturn(Result.Success(weatherResponse))

        weatherViewModel.fetchWeather(city)

        val loadingState = weatherViewModel.weatherState.first()
        assertEquals(Result.Loading, loadingState)

        val successState = weatherViewModel.weatherState.first { it is Result.Success }
        assert(successState is Result.Success)
        assertEquals(weatherResponse, (successState as Result.Success).data)
    }

    @Test
    fun `fetchWeather sets loading and error state`() = runBlockingTest {
        val city = "San Francisco"
        val exception = RuntimeException("Network error")
        whenever(weatherRepository.getWeather(city)).thenReturn(Result.Error(exception))

        weatherViewModel.fetchWeather(city)

        val loadingState = weatherViewModel.weatherState.first()
        assertEquals(Result.Loading, loadingState)

        val errorState = weatherViewModel.weatherState.first { it is Result.Error }
        assert(errorState is Result.Error)
        assertEquals(exception, (errorState as Result.Error).exception)
    }
}
