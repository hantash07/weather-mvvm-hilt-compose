package com.hantash.weather_app.data.repo

import com.hantash.weather_app.data.db.FavoriteDao
import com.hantash.weather_app.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.fetchFavorites().flowOn(Dispatchers.IO).conflate()
    suspend fun addFavorite(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun removeFavorite(favorite: Favorite) = favoriteDao.delete(favorite)
}