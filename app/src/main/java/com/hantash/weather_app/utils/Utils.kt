package com.hantash.weather_app.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(timeStamp: Long): String {
    // Convert Unix timestamp to LocalDateTime
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault())

    // Format the LocalDateTime into a readable string
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM")
    val formattedDateTime = dateTime.format(formatter)

    return formattedDateTime
}

fun formatDateDayOnly(timeStamp: Long): String {
    // Convert Unix timestamp to LocalDateTime
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault())

    // Format the LocalDateTime into a readable string
    val formatter = DateTimeFormatter.ofPattern("EEE")
    val formattedDateTime = dateTime.format(formatter)

    return formattedDateTime
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