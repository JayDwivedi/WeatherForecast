package com.jay.weatherforecastapp.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jay.weatherforecastapp.util.Result

import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.*
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel

import kotlinx.coroutines.flow.MutableStateFlow

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        val weatherViewModel = mock<WeatherViewModel>()
        whenever(weatherViewModel.weatherState).thenReturn(MutableStateFlow(Result.Loading))

        composeTestRule.setContent {
            WeatherScreen(viewModel = weatherViewModel)
        }

        composeTestRule.onNodeWithText("City").assertExists()
        composeTestRule.onNodeWithText("Get Weather").assertExists()
       // composeTestRule.onNode(isInstanceOf(CircularProgressIndicator::class.java)).assertExists()
    }

    @Test
    fun testSuccessState() {
        val weatherResponse = WeatherResponse("20 °C", "10 km/h", "Clear", emptyList())
        val weatherViewModel = mock<WeatherViewModel>()
        whenever(weatherViewModel.weatherState).thenReturn(MutableStateFlow(Result.Success(weatherResponse)))

        composeTestRule.setContent {
            WeatherScreen(viewModel = weatherViewModel)
        }

        composeTestRule.onNodeWithText("City").assertExists()
        composeTestRule.onNodeWithText("Get Weather").assertExists()
        composeTestRule.onNodeWithText("Temperature: 20 °C").assertExists()
        composeTestRule.onNodeWithText("Wind: 10 km/h").assertExists()
        composeTestRule.onNodeWithText("Description: Clear").assertExists()
    }

    @Test
    fun testErrorState() {
        val exception = RuntimeException("Network error")
        val weatherViewModel = mock<WeatherViewModel>()
        whenever(weatherViewModel.weatherState).thenReturn(MutableStateFlow(Result.Error(exception)))

        composeTestRule.setContent {
            WeatherScreen(viewModel = weatherViewModel)
        }

        composeTestRule.onNodeWithText("City").assertExists()
        composeTestRule.onNodeWithText("Get Weather").assertExists()
        composeTestRule.onNodeWithText("Error: Network error").assertExists()
    }
}
