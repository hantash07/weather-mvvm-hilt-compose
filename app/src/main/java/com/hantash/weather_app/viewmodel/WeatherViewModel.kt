package com.hantash.weather_app.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.data.repo.WeatherRepository
import com.hantash.weather_app.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    suspend fun fetchCurrentWeather(city: String): ResultAPI<WeatherResponse, Boolean, Exception>  {
        return repository.fetchCurrentWeather(city)
    }
}