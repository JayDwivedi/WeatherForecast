package com.jay.weatherforecastapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel
import com.jay.weatherforecastapp.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()

    var city by remember { mutableStateOf("San Francisco") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather App") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.fetchWeather(city) }) {
                    Text("Get Weather")
                }
                Spacer(modifier = Modifier.height(16.dp))
                when (weatherState) {
                    is Result.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Result.Success -> {
                        val weather = (weatherState as Result.Success).data
                        Text("Temperature: ${weather.temperature}")
                        Text("Wind: ${weather.wind}")
                        Text("Description: ${weather.description}")
                    }
                    is Result.Error -> {
                        Text("Error: ${(weatherState as Result.Error).exception.message}")
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherScreen()
}
