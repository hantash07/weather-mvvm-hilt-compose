package com.hantash.weather_app.data.repo

import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.data.api.WeatherAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val weatherAPI: WeatherAPI
) {

    suspend fun fetchCurrentWeather(cityName: String): ResultAPI {
         return withContext(Dispatchers.IO) {
            try {
                val response = weatherAPI.getWeather(city = cityName)
                if (response.isSuccessful && response.body() != null){
                    return@withContext ResultAPI.SUCCESS(response.body()!!)
                } else {
                    return@withContext ResultAPI.FAILURE
                }
            } catch (t: Throwable) {
                return@withContext ResultAPI.FAILURE
            }
        }
    }

}