package com.example.wildfiretracker.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

@Entity(tableName = "saved_locations")
data class SavedLocation(
    @PrimaryKey(autoGenerate = false)
    val city: String,
    @Embedded("city_")
    val cityCoordinates: LatLng,
    val sendAlerts: Boolean,
    val hasLiveEvent: Boolean,
    @Embedded("last_event_")
    val lastEventLocation: LatLng?,
    val lastEventDate: LocalDate?
)

