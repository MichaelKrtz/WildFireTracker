package com.example.wildfiretracker.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wildfiretracker.db.entities.SavedLocation
import com.example.wildfiretracker.network.eonetapi.WildFiresResponse
import com.example.wildfiretracker.repositories.SavedLocationsRepository
import com.example.wildfiretracker.util.LatLngCalculations
import com.example.wildfiretracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedLocationsViewModel @Inject constructor(
    private val savedLocationsRepository: SavedLocationsRepository
) : BaseViewModel() {

    private val TAG = "SavedLocationsViewModel"
    lateinit var savedLocations: LiveData<List<SavedLocation>>
    val savedLocationsWildFires: LiveData<Resource<WildFiresResponse>>
        get() = _savedLocationsWildFires
    private val _savedLocationsWildFires: MutableLiveData<Resource<WildFiresResponse>> = MutableLiveData()



    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            savedLocations = savedLocationsRepository.getAllSavedLocationsSortedByCity()
        }
    }

    fun retrieveSavedLocationsWildFireData() {
        Log.v(TAG, "Refresh Locations")
        savedLocations.value?.let {
            Log.v(TAG, savedLocations.value!!.toString())
            for(location in savedLocations.value!!) {
                Log.v(TAG, "here is the location $location")
                val boxCoordinates = getCoordinatesListForLatLngBox(location)
                viewModelScope.launch {
                    try {
                        val response = wildFiresRepository.getWildFiresInLatLngBox(boxCoordinates)
                        val savedLocationWildFires = handleWildFiresResponse(response)
                        if (savedLocationWildFires is Resource.Success) {
                            updateLocationLiveEventState(savedLocationWildFires, location)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                Log.v(TAG, location.city)
            }
        }
    }

    private suspend fun updateLocationLiveEventState(
        savedLocationWildFires: Resource<WildFiresResponse>,
        location: SavedLocation
    ) {
        savedLocationWildFires.data?.let {
            if (it.events.isNotEmpty() && !location.hasLiveEvent) {
                savedLocationsRepository.updateHasLiveEventState(true, location.city)
            } else if (it.events.isEmpty() && location.hasLiveEvent) {
                savedLocationsRepository.updateHasLiveEventState(false, location.city)
            }
        }
    }

    private fun getCoordinatesListForLatLngBox(location: SavedLocation): List<Double> {
        val boxTopLeft = LatLngCalculations.topLeftKmAwayLatLng(
            location.cityCoordinates, 500
        )
        val boxBottomRight = LatLngCalculations.bottomRightKmAwayLatLng(
            location.cityCoordinates, 500
        )
        return listOf<Double>(
            boxTopLeft.longitude,
            boxTopLeft.latitude,
            boxBottomRight.longitude,
            boxBottomRight.latitude
        )
    }


    fun insertSavedLocation(location: SavedLocation) {
        viewModelScope.launch {
            savedLocationsRepository.insertSavedLocation(location)
        }
    }

    fun deleteSavedLocation(location: SavedLocation) {
        viewModelScope.launch {
            savedLocationsRepository.deleteSavedLocation(location)
        }
    }


}