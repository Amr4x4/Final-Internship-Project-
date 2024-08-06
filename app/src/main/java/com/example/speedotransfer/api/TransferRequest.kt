package com.example.speedotransfer.api

data class TransferRequest(
    val senderAccountId: Int,
    val recipientAccountId: Int,
    val amount: Double,
    val currency: String,
    val status: String,
    val description: String
)