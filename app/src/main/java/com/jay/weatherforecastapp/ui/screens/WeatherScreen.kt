package com.jay.weatherforecastapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val weather by viewModel.weather.collectAsState()

    var city by remember { mutableStateOf("San Francisco") }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        weather?.let {
            Text("Temperature: ${it.temperature}")
            Text("Wind: ${it.wind}")
            Text("Description: ${it.description}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherScreen()
}
