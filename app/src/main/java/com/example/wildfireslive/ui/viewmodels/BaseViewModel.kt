package com.example.wildfireslive.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wildfireslive.network.eonetapi.WildFiresResponse
import com.example.wildfireslive.repositories.WildFiresRepository
import com.example.wildfireslive.util.Resource
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    private val TAG = "BaseViewModel"

    protected val wildFiresRepository: WildFiresRepository = WildFiresRepository()

    protected fun handleWildFiresResponse(response: Response<WildFiresResponse>) : Resource<WildFiresResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}