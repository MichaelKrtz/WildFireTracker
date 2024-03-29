package com.example.wildfiretracker.network.eonetapi

data class Geometry(
    val coordinates: List<Double>,
    val date: String,
    val magnitudeUnit: Any?,
    val magnitudeValue: Any?,
    val type: String
)