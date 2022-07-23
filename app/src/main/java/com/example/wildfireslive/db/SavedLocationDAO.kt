package com.example.wildfireslive.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wildfireslive.db.entities.SavedLocation

@Dao
interface SavedLocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLocation(savedLocation: SavedLocation)

    @Delete
    suspend fun deleteSavedLocation(savedLocation: SavedLocation)

    @Query("SELECT * FROM saved_locations ORDER BY city ASC")
    fun getAllSavedLocationsSortedByCity(): LiveData<List<SavedLocation>>

}