package com.hantash.weather_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hantash.weather_app.data.repo.SettingsRepository
import com.hantash.weather_app.ui.components.EnumUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewmodel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {

    fun changeTempUnit(unit: EnumUnit) {
        viewModelScope.launch {
            repository.changeTempUnit(unit)
        }
    }

    fun getTempUnit(): Flow<EnumUnit> {
        return repository.getTempUnit()
    }

}