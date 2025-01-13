package com.hantash.weather_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hantash.weather_app.data.repo.FavoriteRepository
import com.hantash.weather_app.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class FavoriteViewmodel @Inject constructor(
    private val repository: FavoriteRepository
): ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    fun fetchFavorites() {
        viewModelScope.launch {
            repository.getFavorites().distinctUntilChanged().collect {
                _favoriteList.value = it
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }
}