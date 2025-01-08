package com.hantash.weather_app.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    val weatherData: MutableState<ResultAPI> = mutableStateOf(ResultAPI.INITIATE)

    init {
        fetchCurrentWeather("California")
    }

    private fun fetchCurrentWeather(cityName: String) {
        viewModelScope.launch {
            weatherData.value = repository.fetchCurrentWeather(cityName)
        }
    }
}