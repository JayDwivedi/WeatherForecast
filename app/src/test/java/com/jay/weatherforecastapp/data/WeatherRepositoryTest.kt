package com.jay.weatherforecastapp.data

import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.data.remote.WeatherApiService
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherApiService = mock()
        weatherRepository = WeatherRepository(weatherApiService)
    }

    @Test
    fun `getWeather returns success`() = runTest {
        val city = "San Francisco"
        val weatherResponse = WeatherResponse("20 °C", "10 km/h", "Clear", emptyList())
        whenever(weatherApiService.getWeather(city)).thenReturn(weatherResponse)

        val result = weatherRepository.getWeather(city)

        assert(result is Result.Success)
        assertEquals(weatherResponse, (result as Result.Success).data)
    }

    @Test
    fun `getWeather returns error`() = runTest {
        val city = "San Francisco"
        val exception = RuntimeException("Network error")
        whenever(weatherApiService.getWeather(city)).thenThrow(exception)

        val result = weatherRepository.getWeather(city)

        assert(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
