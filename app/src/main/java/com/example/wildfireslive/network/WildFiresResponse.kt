package com.example.wildfireslive.network

data class WildFiresResponse(
    val description: String,
    val events: List<Event>,
    val link: String,
    val title: String
)