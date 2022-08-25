package com.example.wildfireslive.di

import android.content.Context
import androidx.room.Room
import com.example.wildfireslive.db.SavedLocationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSavedLocationDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app.applicationContext,
        SavedLocationsDatabase::class.java,
        "saved_locations_db"
    ).build()

    @Singleton
    @Provides
    fun provideSavedLocationDao(db: SavedLocationsDatabase) = db.savedLocationDAO
}