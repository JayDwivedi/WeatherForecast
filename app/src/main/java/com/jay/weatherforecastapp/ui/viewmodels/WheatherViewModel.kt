package com.jay.weatherforecastapp.ui.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherResponse>> = _weatherState

    fun fetchWeather(city: String) {
        _weatherState.value = Result.Loading
        viewModelScope.launch {
            val result = repository.getWeather(city)
            _weatherState.value = result
        }
    }
}
