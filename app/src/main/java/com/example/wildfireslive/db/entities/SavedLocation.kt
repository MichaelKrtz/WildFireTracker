package com.example.wildfireslive.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

@Entity(tableName = "saved_locations")
data class SavedLocation(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val city: String,
    @Embedded
    val location: LatLng,
    val date: LocalDate,
    val sendAlerts: Boolean,
    val hasLiveEvent: Boolean,
)

