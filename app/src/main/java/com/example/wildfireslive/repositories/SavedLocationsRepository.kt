package com.example.wildfireslive.repositories

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.wildfireslive.db.entities.SavedLocation
import com.example.wildfireslive.db.SavedLocationDAO
import com.example.wildfireslive.db.SavedLocationsDatabase
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

class SavedLocationsRepository {

    companion object {
        lateinit var context: Context
    }


    private val db = SavedLocationsDatabase.getInstance(context)
    private val savedLocationDao: SavedLocationDAO = db.savedLocationDAO


    suspend fun insertSavedLocation(savedLocation: SavedLocation) = savedLocationDao.insertSavedLocation(savedLocation)

    suspend fun deleteSavedLocation(savedLocation: SavedLocation) = savedLocationDao.deleteSavedLocation(savedLocation)

    fun getAllSavedLocationsSortedById() = savedLocationDao.getAllSavedLocationsSortedById()

}