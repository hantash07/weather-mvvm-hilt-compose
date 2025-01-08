package com.hantash.weather_app.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)