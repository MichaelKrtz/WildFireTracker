package com.example.wildfiretracker.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.cos


object LatLngCalculations {

    //Latitude: 1 deg = 110.574 km
    //Longitude: 1 deg = 111.320*cos(latitude) km
    fun topLeftKmAwayLatLng(coordinates: LatLng, km: Long): LatLng {
        var topLeftLat: Double = coordinates.latitude + (km / 110.574)
        if (topLeftLat > 90) {
            topLeftLat = -topLeftLat.mod(90.0)
        }
        var topLeftLng: Double = coordinates.longitude - (km / (111.320 * cos(Math.toRadians(topLeftLat))))
        if (topLeftLng < -180) {
            topLeftLng = topLeftLng.mod(-180.0)
        }
        return LatLng(topLeftLat, topLeftLng)
    }

    fun bottomRightKmAwayLatLng(coordinates: LatLng, km: Long): LatLng {
        var bottomRightLat: Double = coordinates.latitude - (km / 110.574)
        if (bottomRightLat < -90) {
            bottomRightLat = bottomRightLat.mod(-90.0)
        }
        var bottomRightLng: Double = coordinates.longitude + (km / (111.320 * cos(Math.toRadians(bottomRightLat))))
        if (bottomRightLng > 180) {
            bottomRightLng = -bottomRightLng.mod(180.0)
        }
        return LatLng(bottomRightLat, bottomRightLng)    }
}