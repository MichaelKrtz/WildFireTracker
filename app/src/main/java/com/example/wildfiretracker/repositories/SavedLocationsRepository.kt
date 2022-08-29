package com.example.wildfiretracker.repositories

import com.example.wildfiretracker.db.entities.SavedLocation
import com.example.wildfiretracker.db.SavedLocationDAO
import javax.inject.Inject

class SavedLocationsRepository @Inject constructor(
    private val savedLocationDao: SavedLocationDAO
) {

    suspend fun insertSavedLocation(savedLocation: SavedLocation) = savedLocationDao.insertSavedLocation(savedLocation)

    suspend fun deleteSavedLocation(savedLocation: SavedLocation) = savedLocationDao.deleteSavedLocation(savedLocation)

    fun getAllSavedLocationsSortedByCity() = savedLocationDao.getAllSavedLocationsSortedByCity()

    suspend fun updateHasLiveEventState(hasLiveEvent: Boolean, cityName: String) =
        savedLocationDao.updateHasLiveEventState(hasLiveEvent, cityName)

}