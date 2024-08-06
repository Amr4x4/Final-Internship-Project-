package com.example.speedotransfer.api

data class LoginResponse(
    val token: String,
    val name: String,
    val balance: Double,
    val favorites: List<Favorite>
)

