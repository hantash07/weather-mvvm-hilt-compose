package com.hantash.weather_app.model

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Double
)