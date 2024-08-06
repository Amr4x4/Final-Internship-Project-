package com.example.speedotransfer.api

import android.accounts.Account
import com.example.speedotransfer.app_core.Transaction
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Any>

    @PUT("/api/updatePassword")
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): Response<Any>

    @POST("/api/transfer")
    suspend fun transfer(@Body request: TransferRequest): Response<Any>

    @GET("/api/accounts/{id}")
    suspend fun getAccount(@Path("id") id: Int): Response<Account>

    @PUT("/api/accounts/{id}")
    suspend fun updateAccount(@Path("id") id: Int, @Body request: AccountUpdateRequest): Response<Any>

    @DELETE("/api/accounts/{id}")
    suspend fun deleteAccount(@Path("id") id: Int): Response<Any>

    @GET("/api/customer/{id}")
    suspend fun getCustomer(@Path("id") id: Int): Response<Customer>

    @GET("/api/transactionHistory")
    suspend fun getTransactionHistory(
        @Query("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<Transaction>>

    @POST("/api/favorites/user/{id}")
    suspend fun addFavorite(@Path("id") userId: Int, @Body favorite: Favorite): Response<Any>

    @GET("/api/favorites/user/{id}")
    suspend fun getFavorites(@Path("id") userId: Int): Response<List<Favorite>>

    @DELETE("/api/favorites/user/{id}")
    suspend fun deleteFavorite(@Path("id") userId: Int): Response<Any>

    @GET("/api/accounts/balance")
    suspend fun getBalance(@Query("accountId") accountId: Int): Response<Double>
}