package com.jay.weatherforecastapp.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWeatherScreenLoadingState() {
        // Given
        val viewModel = mock<WeatherViewModel>()
        val weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
        whenever(viewModel.weatherState).thenReturn(weatherState)

        // When
        composeTestRule.setContent { WeatherScreen(viewModel = viewModel) }

        // Then
        composeTestRule.onNodeWithText("Get Weather").assertExists()
        composeTestRule.onNodeWithText("Temperature:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Wind:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Description:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Error:").assertDoesNotExist()
    }

    @Test
    fun testWeatherScreenSuccessState() {
        // Given
        val viewModel = mock<WeatherViewModel>()
        val weatherResponse = WeatherResponse(
            temperature = "20 °C",
            wind = "10 km/h",
            description = "Clear",
            forecast = emptyList()
        )
        val weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Success(weatherResponse))
        whenever(viewModel.weatherState).thenReturn(weatherState)

        // When
        composeTestRule.setContent { WeatherScreen(viewModel = viewModel) }

        // Then
        composeTestRule.onNodeWithText("Get Weather").assertDoesNotExist()
        composeTestRule.onNodeWithText("Temperature: 20 °C").assertExists()
        composeTestRule.onNodeWithText("Wind: 10 km/h").assertExists()
        composeTestRule.onNodeWithText("Description: Clear").assertExists()
        composeTestRule.onNodeWithText("Error:").assertDoesNotExist()
    }

    @Test
    fun testWeatherScreenErrorState() {
        // Given
        val viewModel = mock<WeatherViewModel>()
        val errorMessage = "Network error"
        val errorResponse = Result.Error(Exception(errorMessage))
        val weatherState = MutableStateFlow<Result<WeatherResponse>>(errorResponse)
        whenever(viewModel.weatherState).thenReturn(weatherState)

        // When
        composeTestRule.setContent { WeatherScreen(viewModel = viewModel) }

        // Then
        composeTestRule.onNodeWithText("Get Weather").assertExists()
        composeTestRule.onNodeWithText("Temperature:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Wind:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Description:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Error: $errorMessage").assertExists()
    }
}
