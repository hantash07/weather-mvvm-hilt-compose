package com.hantash.weather_app.data.api

class ResultAPI<T, Boolean, E: Exception>(
    var data: T? = null,
    var isLoading: kotlin.Boolean = false,
    var exception: E? = null,
)