package com.example.mss.uiapp.model

data class Rooms(
    val name: String,
    val location: String,
    val reservations: List<Reservations>

)