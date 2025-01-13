package com.hantash.weather_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Favorite(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val cityName: String,
)
