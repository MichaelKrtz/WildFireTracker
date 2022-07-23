package com.example.wildfireslive.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wildfireslive.db.entities.SavedLocation

@Database(
    entities = [SavedLocation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SavedLocationsDatabase : RoomDatabase() {

    abstract val savedLocationDAO: SavedLocationDAO

    companion object {
        @Volatile
        private var INSTANCE: SavedLocationsDatabase? = null

        fun getInstance(context: Context): SavedLocationsDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SavedLocationsDatabase::class.java,
                    "saved_locations_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}