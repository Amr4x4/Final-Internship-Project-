package com.example.speedotransfer.api

import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    suspend fun register(request: RegisterRequest) = apiService.register(request)

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }

    suspend fun logout(token: String) = apiService.logout(token)
    suspend fun updatePassword(request: UpdatePasswordRequest) = apiService.updatePassword(request)
    suspend fun transfer(request: TransferRequest) = apiService.transfer(request)
    suspend fun getAccount(id: Int) = apiService.getAccount(id)
    suspend fun updateAccount(id: Int, request: AccountUpdateRequest) = apiService.updateAccount(id, request)
    suspend fun deleteAccount(id: Int) = apiService.deleteAccount(id)
    suspend fun getCustomer(id: Int) = apiService.getCustomer(id)
    suspend fun getTransactionHistory(accountId: Int, startDate: String, endDate: String) = apiService.getTransactionHistory(accountId, startDate, endDate)
    suspend fun addFavorite(userId: Int, favorite: Favorite) = apiService.addFavorite(userId, favorite)
    suspend fun getFavorites(userId: Int) = apiService.getFavorites(userId)
    suspend fun deleteFavorite(userId: Int) = apiService.deleteFavorite(userId)
    suspend fun getBalance(accountId: Int) = apiService.getBalance(accountId)
    suspend fun registerUser(request: RegisterRequest) = apiService.registerUser(request)

}
