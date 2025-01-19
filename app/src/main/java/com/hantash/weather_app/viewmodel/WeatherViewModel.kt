package com.hantash.weather_app.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hantash.weather_app.data.api.ResponseApi
import com.hantash.weather_app.data.repo.WeatherRepository
import com.hantash.weather_app.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    private val _weatherDataState = MutableStateFlow<ResponseApi<WeatherResponse>>(ResponseApi.Loading())
    val weatherDataState: StateFlow<ResponseApi<WeatherResponse>> = _weatherDataState

    fun fetchCurrentWeather(city: String)  {
        viewModelScope.launch {
            _weatherDataState.value = ResponseApi.Loading()
            val result = repository.fetchCurrentWeather(city)
            _weatherDataState.value = result
        }
    }
}