package com.example.wildfireslive.repositories

import com.example.wildfireslive.network.eonetapi.EonetApi
import com.example.wildfireslive.network.eonetapi.WildFiresResponse
import retrofit2.Response

class WildFiresRepository() {

    suspend fun getWildFires(): Response<WildFiresResponse> =
        EonetApi.retrofitService.getWildFires()
}