package com.hantash.weather_app.data.repo

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.hantash.weather_app.utils.debug
import java.util.Locale

class LocationRepository(
    private val fusedLocationProvider: FusedLocationProviderClient,
    private val geocoder: Geocoder,
) {
    private val _city = MutableLiveData<String?>()
    val cityLiveData: LiveData<String?> = _city

    @SuppressLint("MissingPermission")
    fun getUserCurrentCity(): LiveData<String?> {
        fusedLocationProvider.lastLocation
            .addOnSuccessListener { location ->
                _city.value = getCityFromLocation(location)
            }
            .addOnFailureListener {
                debug("Failed to get location: ${it.message}")
            }
        return _city
    }

    private fun getCityFromLocation(location: Location?): String? {
        if (location == null) return null

        return try {
            val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address = addressList?.first()
            address?.locality
        } catch (e: Exception) {
            debug("Failed to get reverse geocode the location: ${e.message}")
            null
        }
    }
}
