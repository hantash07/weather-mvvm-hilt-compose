package com.hantash.weather_app.data.repo

import com.hantash.weather_app.data.api.ExceptionApi
import com.hantash.weather_app.data.api.ResponseApi
import com.hantash.weather_app.data.api.WeatherAPI
import com.hantash.weather_app.model.WeatherResponse

class WeatherRepository(
    private val weatherAPI: WeatherAPI
) {

    suspend fun fetchCurrentWeather(cityName: String): ResponseApi<WeatherResponse> {
        return try {
            val response = weatherAPI.getWeather(cityName)
            ResponseApi.Success(data = response.body()!!)
        } catch (e: ExceptionApi) {
            ResponseApi.Error(
                message = e.message ?: "Unknown Error",
                errorCode = e.code
            )
        } catch (e: Exception) {
            ResponseApi.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }

}