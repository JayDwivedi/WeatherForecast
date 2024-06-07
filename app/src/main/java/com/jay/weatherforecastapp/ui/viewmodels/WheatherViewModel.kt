package com.jay.weatherforecastapp.ui.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _weather.value = repository.getWeather(city)
        }
    }
}
