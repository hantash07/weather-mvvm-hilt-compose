package com.hantash.weather_app.di.app

import android.content.Context
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hantash.weather_app.data.api.ErrorInterceptor
import com.hantash.weather_app.data.api.WeatherAPI
import com.hantash.weather_app.data.db.FavoriteDao
import com.hantash.weather_app.data.db.LocalDatabase
import com.hantash.weather_app.utils.Constant
import com.hantash.weather_app.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    fun retrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @AppScope
    fun weatherAPI(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }

    @Provides
    @AppScope
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @AppScope
    fun fusedLocationProvider(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @AppScope
    fun geocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Provides
    @AppScope
    fun localDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            name = "local_db"
        ).fallbackToDestructiveMigrationFrom().build()
    }

    @Provides
    @AppScope
    fun favoriteDao(localDatabase: LocalDatabase): FavoriteDao {
        return localDatabase.favoriteDao()
    }

}