package com.hantash.weather_app.data.api

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            val errorMessage = if (errorBody != null) {
                try {
                    val apiError = Gson().fromJson(errorBody, ErrorApi::class.java)
                    apiError.message
                } catch (e: Exception) {
                    "Error parsing response"
                }
            } else {
                "Unknown error occurred"
            }
            throw ExceptionApi(errorMessage, response.code)
        }
        return response
    }
}

class ExceptionApi(
    message: String,
    val code: Int,
) : IOException(message)

data class ErrorApi(
    val cod: String,
    val message: String
)