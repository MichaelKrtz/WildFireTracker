package com.example.wildfiretracker.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun toString(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun toLocalDate(stringDate: String): LocalDate {
        return LocalDate.parse(stringDate, DateTimeFormatter.ISO_LOCAL_DATE)
    }
//
//    @TypeConverter
//    fun toCoordinates(location: LatLng): Coordinates {
//        return Coordinates(location.latitude, location.longitude)
//    }
//
//    @TypeConverter
//    fun toLatLng(coordinates: Coordinates?): LatLng? {
//        if(coordinates != null) {
//            return LatLng(coordinates.latitude, coordinates.longitude)
//        }
//    }
}