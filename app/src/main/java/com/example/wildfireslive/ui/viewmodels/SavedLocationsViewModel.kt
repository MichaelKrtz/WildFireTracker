package com.example.wildfireslive.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wildfireslive.db.entities.SavedLocation
import com.example.wildfireslive.network.eonetapi.WildFiresResponse
import com.example.wildfireslive.repositories.SavedLocationsRepository
import com.example.wildfireslive.util.LatLngCalculations
import com.example.wildfireslive.util.Resource
import kotlinx.coroutines.launch

class SavedLocationsViewModel(context: Context) : BaseViewModel() {

    private val TAG = "SavedLocationsViewModel"
    private val savedLocationsRepository: SavedLocationsRepository
    lateinit var savedLocations: LiveData<List<SavedLocation>>
    val savedLocationsWildFires: LiveData<Resource<WildFiresResponse>>
        get() = _savedLocationsWildFires
    private val _savedLocationsWildFires: MutableLiveData<Resource<WildFiresResponse>> = MutableLiveData()



    init {
        SavedLocationsRepository.context = context
        savedLocationsRepository = SavedLocationsRepository()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            savedLocations = savedLocationsRepository.getAllSavedLocationsSortedByCity()
        }
    }

    fun retrieveSavedLocationsWildFireData(savedLocationsList: List<SavedLocation>) {
        Log.v(TAG, "Refresh Locations")
        for(location in savedLocationsList) {
            val boxCoordinates = getCoordinatesListForLatLngBox(location)
            viewModelScope.launch {
                try {
                    val response = wildFiresRepository.getWildFiresInLatLngBox(boxCoordinates)
                    //_savedLocationsWildFires.postValue(handleWildFiresResponse(response))
                    val savedLocationWildFires = handleWildFiresResponse(response)
                    if (savedLocationWildFires is Resource.Success) {
                        if (savedLocationWildFires.data?.events?.size!! > 0) {
                            savedLocationsRepository.updateHasLiveEventState(true, location.city)
                        }
                        else {
                            savedLocationsRepository.updateHasLiveEventState(false, location.city)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

//    private fun hasWildFiresInLatLngBox(coordinates: List<Double>) {
////        var savedLocationWildFires: Resource<WildFiresResponse>
//        viewModelScope.launch {
//            //_savedLocationsWildFires.postValue(Resource.Loading())
////            savedLocationWildFires = Resource.Loading()
////            try {
////                val response = wildFiresRepository.getWildFiresInLatLngBox(coordinates)
////                //_savedLocationsWildFires.postValue(handleWildFiresResponse(response))
////                savedLocationWildFires = handleWildFiresResponse(response)
////            } catch (e: Exception) {
////                e.printStackTrace()
////            }
//
//
//    }

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