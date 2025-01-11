package com.hantash.weather_app.utils

import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class EnumDateFormat(val value: String) {
    EEE_D_MMM("EEEE, d MMM"),
    EEE("EEE"),
    HH_MM_A("hh:mm:a")
}

fun formatDateTime(timeStamp: Long?, enumDateFormat: EnumDateFormat): String {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp ?: getCurrentTimeStamp()), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern(enumDateFormat.value)
    val formattedDateTime = dateTime.format(formatter)

    return formattedDateTime
}

fun getCurrentTimeStamp(): Long {
    val today = LocalDateTime.now()

    return today.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun formatDecimals(item: Double): String {
    return "%.0f".format(item)
}

fun generateImageUrl(image: String?): String {
    return "https://openweathermap.org/img/wn/$image.png"
}