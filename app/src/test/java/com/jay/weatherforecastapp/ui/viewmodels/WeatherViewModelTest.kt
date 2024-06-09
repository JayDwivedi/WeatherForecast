package com.jay.weatherforecastapp.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jay.weatherforecastapp.MainCoroutineRule
import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    // Executes each task synchronously using Architecture Components
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setUp() {
        weatherRepository = mock<WeatherRepository>()
        weatherViewModel = WeatherViewModel(weatherRepository)
    }


    @Test
    fun `fetchWeather returns success`() = runTest{
        // Mock data
        val city = "San Francisco"
        val weatherResponse = WeatherResponse("20 °C", "10 km/h", "Clear", emptyList())
        whenever(weatherRepository.getWeather(city)).thenReturn(Result.Success(weatherResponse))

        // Call the function to be tested
        weatherViewModel.fetchWeather(city)


        assertEquals(Result.Success(weatherResponse), weatherViewModel.weatherState.value)
    }

    @Test
    fun `fetchWeather returns error`() = runTest {
        // Mock data
        val city = "San Francisco"
        val errorMessage = "Network error"
        val exception = RuntimeException(errorMessage)
        whenever(weatherRepository.getWeather(city)).thenReturn(Result.Error(exception))

        // Call the function to be tested
        weatherViewModel.fetchWeather(city)


        assertEquals(true, (weatherViewModel.weatherState.value as? Result.Error)?.exception != null)
    }
}
