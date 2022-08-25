package com.example.wildfireslive.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wildfireslive.network.eonetapi.WildFiresResponse
import com.example.wildfireslive.util.Resource
import kotlinx.coroutines.launch


class FireMapViewModel : BaseViewModel() {
    private val TAG = "FireMapViewModel"
    val wildFires: LiveData<Resource<WildFiresResponse>>
        get() = _wildFires
    private val _wildFires: MutableLiveData<Resource<WildFiresResponse>> = MutableLiveData()

    init {
        getWildFires()
    }
    private fun getWildFires() {
        viewModelScope.launch {
            _wildFires.postValue(Resource.Loading())
            try {
                val response = wildFiresRepository.getWildFires()
                _wildFires.postValue(handleWildFiresResponse(response))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}