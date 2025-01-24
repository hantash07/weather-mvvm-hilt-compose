package com.hantash.weather_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hantash.weather_app.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewmodel @Inject constructor(
    private val repository: LocationRepository
): ViewModel() {
    val cityLiveData: LiveData<String?> = repository.cityLiveData

    fun getUserCurrentCity() {
        repository.getUserCurrentCity()
    }
}