package com.example.wildfireslive.network.eonetapi

data class Event(
    val categories: List<Category>,
    val closed: Any?,
    val description: Any?,
    val geometry: List<Geometry>,
    val id: String,
    val link: String,
    val sources: List<Source>,
    val title: String
)