package com.hantash.weather_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val city: String,
    val country: String
)
