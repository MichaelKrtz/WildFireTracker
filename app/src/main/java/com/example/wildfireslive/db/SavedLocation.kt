package com.example.wildfireslive.db

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.util.*

data class SavedLocation(
    val city: String,
    val location: LatLng,
    val date: LocalDate,
    val sendAlerts: Boolean,
    val hasLiveEvent: Boolean
)
