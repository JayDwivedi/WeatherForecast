package com.jay.weatherforecastapp.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWeatherScreen() {
        composeTestRule.setContent {
            WeatherScreen()
        }

        composeTestRule.onNodeWithText("City").performTextInput("San Francisco")
        composeTestRule.onNodeWithText("Get Weather").performClick()
        // Add assertions to check if the UI updates correctly
    }
}
