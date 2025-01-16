package com.hantash.weather_app.di.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hantash.weather_app.data.api.WeatherAPI
import com.hantash.weather_app.data.db.FavoriteDao
import com.hantash.weather_app.data.repo.FavoriteRepository
import com.hantash.weather_app.data.repo.SettingsRepository
import com.hantash.weather_app.data.repo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun weatherRepository(weatherAPI: WeatherAPI) = WeatherRepository(weatherAPI)

    @Provides
    @ViewModelScoped
    fun favoriteRepository(favoriteDao: FavoriteDao) = FavoriteRepository(favoriteDao)

    @Provides
    @ViewModelScoped
    fun settingsRepository(dataStore: DataStore<Preferences>) = SettingsRepository(dataStore)
}