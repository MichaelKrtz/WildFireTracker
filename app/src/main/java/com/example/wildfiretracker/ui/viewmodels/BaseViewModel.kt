package com.example.wildfiretracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wildfiretracker.network.eonetapi.WildFiresResponse
import com.example.wildfiretracker.repositories.WildFiresRepository
import com.example.wildfiretracker.util.Resource
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