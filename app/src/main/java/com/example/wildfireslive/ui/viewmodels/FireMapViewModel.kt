package com.example.wildfireslive.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wildfireslive.network.EonetApi
import com.example.wildfireslive.network.WildFiresResponse
import com.example.wildfireslive.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class FireMapViewModel : ViewModel(){

    private val _status = MutableLiveData<String>()

    private val TAG = "FireMapViewModel"

    val status: LiveData<String> = _status

    val wildFires: MutableLiveData<Resource<WildFiresResponse>> = MutableLiveData()

    init {
        getWildFires()
    }

    private fun getWildFires() {
        viewModelScope.launch {
            wildFires.postValue(Resource.Loading())
            try {
                val response = EonetApi.retrofitService.getWildFires()
                wildFires.postValue(handleWildFiresResponse(response))
                _status.value = "Success: ${response.body()?.events?.size} WildFires retrieved"
                Log.v(TAG, status.value!!)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.v(TAG, "Something Went Wrong")
            }
        }
    }

    private fun handleWildFiresResponse(response: Response<WildFiresResponse>) : Resource<WildFiresResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}