package com.hantash.weather_app.data.api

import com.hantash.weather_app.model.WeatherResponse

sealed class ResultAPI {
    class SUCCESS(val weatherResponse: WeatherResponse?): ResultAPI()
    data object FAILURE: ResultAPI()
    data object INITIATE: ResultAPI()
}