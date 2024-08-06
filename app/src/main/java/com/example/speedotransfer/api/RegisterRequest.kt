package com.example.speedotransfer.api

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val nationality: String,
    val nationalIdNumber: String,
    val gender: String,
    val dateOfBirth: String,
    val password: String
){}