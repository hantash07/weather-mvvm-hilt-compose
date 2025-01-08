package com.hantash.weather_app.data.api

import com.hantash.weather_app.model.WeatherResponse
import com.hantash.weather_app.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/forecast/daily?appid=${Constant.API_KEY}&units=imperial")
    suspend fun getWeather(@Query("q") city: String): Response<WeatherResponse>
}