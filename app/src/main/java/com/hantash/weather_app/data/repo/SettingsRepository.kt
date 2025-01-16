package com.hantash.weather_app.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataPreference: DataStore<Preferences>) {
    private val tempUnitKey = stringPreferencesKey("temperature_unit_key")

    suspend fun changeTempUnit(unit: String) {
        dataPreference.edit { preferences ->
            preferences[tempUnitKey] = unit
        }
    }

    fun getTempUnit(): Flow<String?> {
        return dataPreference.data.map { preferences ->
            preferences[tempUnitKey]
        }
    }

}