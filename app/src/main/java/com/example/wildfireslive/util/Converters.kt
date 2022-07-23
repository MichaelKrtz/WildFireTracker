package com.example.wildfireslive.util

import com.google.android.gms.maps.model.LatLng

object Converters {
    fun latLngToKilometers(coordinates: LatLng): Long {
        return 1000
    }

    fun kilometersToLatLng(distance: Long): LatLng {
        return LatLng(0.0,0.0)
    }
}