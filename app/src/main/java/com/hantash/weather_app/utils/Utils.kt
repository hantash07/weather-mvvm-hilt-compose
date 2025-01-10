package com.hantash.weather_app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timeStamp: Int): String {
    val date = Date(timeStamp.toLong())
    val format = SimpleDateFormat("EEEE, d MMM", Locale.getDefault())

    return format.format(date)
}

fun formatDateDayOnly(timeStamp: Int): String {
    val date = Date(timeStamp.toLong())
    val format = SimpleDateFormat("EEE", Locale.getDefault())

    return format.format(date)
}

fun formatTime(timeStamp: Int): String {
    val date = Date(timeStamp.toLong())
    val format = SimpleDateFormat("hh:mm:aa", Locale.getDefault())

    return format.format(date)
}

fun formatDecimals(item: Double): String {
    return "%.0f".format(item)
}

fun generateImageUrl(image: String?): String {
    return "https://openweathermap.org/img/wn/$image.png"
}