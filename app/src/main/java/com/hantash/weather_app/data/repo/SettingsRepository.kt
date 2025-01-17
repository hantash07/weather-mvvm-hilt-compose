package com.hantash.weather_app.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hantash.weather_app.ui.components.EnumUnit
import com.hantash.weather_app.utils.debug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStorePref: DataStore<Preferences>) {
    private val tempUnitKey = stringPreferencesKey("temperature_unit_key")

    suspend fun changeTempUnit(unit: EnumUnit) {
        dataStorePref.edit { preferences ->
            preferences[tempUnitKey] = unit.name
        }
    }

    fun getTempUnit(): Flow<EnumUnit> {
        val enumUnit : Flow<EnumUnit> = dataStorePref.data.map { preferences ->
            val unitName = preferences[tempUnitKey] ?: EnumUnit.CELSIUS.name
            EnumUnit.valueOf(unitName)
        }
        return enumUnit
    }

}