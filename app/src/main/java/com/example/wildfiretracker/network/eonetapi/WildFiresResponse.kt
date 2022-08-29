package com.example.wildfiretracker.network.eonetapi

data class WildFiresResponse(
    val description: String,
    val events: List<Event>,
    val link: String,
    val title: String
)