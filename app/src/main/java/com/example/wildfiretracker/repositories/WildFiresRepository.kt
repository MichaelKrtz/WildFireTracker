package com.example.wildfiretracker.repositories

import com.example.wildfiretracker.network.eonetapi.EonetApi
import com.example.wildfiretracker.network.eonetapi.WildFiresResponse
import retrofit2.Response

class WildFiresRepository() {

    suspend fun getWildFires(): Response<WildFiresResponse> =
        EonetApi.retrofitService.getWildFires()

    suspend fun getWildFiresInLatLngBox(coordinates: List<Double>): Response<WildFiresResponse> =
        EonetApi.retrofitService.getWildFiresInLatLngBox(
            bbox = coordinates.toString()
                .removeSurrounding("[", "]")
                .filterNot { it.isWhitespace() }
        )

}