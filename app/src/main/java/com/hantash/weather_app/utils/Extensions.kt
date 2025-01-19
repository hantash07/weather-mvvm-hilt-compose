package com.hantash.weather_app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.PREFERENCE_NAME)

fun String.capsFirstLetter(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun String.capsEachWord(): String {
    return  this.split(" ")
        .joinToString(" ") { it.lowercase().replaceFirstChar {char -> char.uppercase() } }
}