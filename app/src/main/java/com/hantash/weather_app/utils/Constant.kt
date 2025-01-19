package com.hantash.weather_app.utils
import com.hantash.weather_app.BuildConfig

object Constant {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = BuildConfig.WEATHER_API_KEY

    const val PREFERENCE_NAME = "settings"

    const val LOCATION_PERMISSION_REQUEST_CODE = 100

    const val KEY_COUNTRY = "country"
    const val APP_DEBUG = "app-debug"
}