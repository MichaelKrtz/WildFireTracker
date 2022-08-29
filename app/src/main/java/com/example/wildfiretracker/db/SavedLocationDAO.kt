package com.example.wildfiretracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wildfiretracker.db.entities.SavedLocation

@Dao
interface SavedLocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLocation(savedLocation: SavedLocation)

    @Delete
    suspend fun deleteSavedLocation(savedLocation: SavedLocation)

    @Query("SELECT * FROM saved_locations")
    fun getAllSavedLocationsSortedByCity(): LiveData<List<SavedLocation>>

    @Query("UPDATE saved_locations SET hasLiveEvent=:hasLiveEvent WHERE city=:cityName")
    suspend fun updateHasLiveEventState(hasLiveEvent: Boolean, cityName: String)

}