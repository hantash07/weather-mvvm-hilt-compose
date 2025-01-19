package com.hantash.weather_app.data.api

sealed class ResponseApi<T> {
    data class Success<T>(val data: T) : ResponseApi<T>()
    data class Error<T>(val message: String, val errorCode: Int? = null) : ResponseApi<T>()
    class Loading<T> : ResponseApi<T>()
}