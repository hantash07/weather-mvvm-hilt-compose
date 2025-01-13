package com.hantash.weather_app.di.app

import android.content.Context
import androidx.room.Room
import com.hantash.weather_app.data.api.WeatherAPI
import com.hantash.weather_app.data.db.FavoriteDao
import com.hantash.weather_app.data.db.LocalDatabase
import com.hantash.weather_app.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun weatherAPI(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
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