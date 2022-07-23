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

    @Query(value = "SELECT * FROM saved_locations")
    fun getAllSavedLocationsSortedById(): LiveData<List<SavedLocation>>

}