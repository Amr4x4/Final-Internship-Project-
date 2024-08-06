package com.example.speedotransfer.api

data class AccountUpdateRequest(
    val id: Int,
    val accountName: String,
    val accountDescription: String,
    val balance: Double,
    val accountType: String,
    val currency: String,
    val active: Boolean
)