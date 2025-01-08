package com.hantash.weather_app.data.repo

import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.data.api.WeatherAPI
import com.hantash.weather_app.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val weatherAPI: WeatherAPI
) {

    suspend fun fetchCurrentWeather(cityName: String): ResultAPI<WeatherResponse, Boolean, Exception> {
        val response = try {
            weatherAPI.getWeather(city = cityName)
        } catch (e: Exception) {
            return ResultAPI(exception = e)
        }
        return ResultAPI(data = response)
    }

}