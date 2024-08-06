package com.example.speedotransfer.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginResult?>(null)
    val loginState: StateFlow<LoginResult?> = _loginState

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _balance = MutableStateFlow<Double?>(null)
    val balance: StateFlow<Double?> = _balance

    private val _favorites = MutableStateFlow<List<Favorite>?>(null)
    val favorites: StateFlow<List<Favorite>?> = _favorites

    private val _registrationState = MutableStateFlow<RegistrationResult?>(null)
    val registrationState: StateFlow<RegistrationResult?> = _registrationState

    fun login(request: LoginRequest, context: Context) {
        viewModelScope.launch {
            try {
                val response = repository.login(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _loginState.value = LoginResult.Success(body)
                        _userName.value = body.name
                        _balance.value = body.balance
                        _favorites.value = body.favorites
                    } else {
                        _loginState.value = LoginResult.Error("Empty response body")
                    }
                } else {
                    _loginState.value = LoginResult.Error(response.message())
                }
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = repository.register(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _registrationState.value = RegistrationResult.Success(body)
                    } else {
                        _registrationState.value = RegistrationResult.Error("Empty response body")
                    }
                } else {
                    _registrationState.value = RegistrationResult.Error(response.message())
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class LoginResult {
    data class Success(val response: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

sealed class RegistrationResult {
    data class Success(val response: RegisterResponse) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}