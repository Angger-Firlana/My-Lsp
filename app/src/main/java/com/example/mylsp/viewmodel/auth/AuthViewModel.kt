package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.auth.LoginRequest
import com.example.mylsp.model.api.auth.RegisterRequest
import com.example.mylsp.repository.auth.AuthRepository
import com.example.mylsp.repository.auth.UserRepository
import com.example.mylsp.util.user.TokenManager
import com.example.mylsp.util.user.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    application: Application,

) : AndroidViewModel(application) {
    private val repository: AuthRepository = AuthRepository(application.applicationContext)
    private val userRepository: UserRepository = UserRepository(application.applicationContext)
    private val tokenManager = TokenManager(application.applicationContext)
    private val userManager = UserManager(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val result = repository.login(loginRequest)
            result.fold(
                onSuccess = { body ->
                    _state.value = true
                    _message.value = body.message

                    val token = body.token
                    Log.d("AuthViewModel", "Token diterima: $token")
                    tokenManager.saveToken(token)
                    Log.d("AuthViewModel", "Token berhasil disimpan")
                },
                onFailure = { error ->
                    _state.value = false
                    _message.value = error.message ?: "Login Gagal : Tidak Diketahui"
                    Log.e("Login Error", error.message.toString())
                }
            )
        }
    }

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            val result = repository.register(registerRequest)
            result.fold(
                onSuccess = { body ->
                    _state.value = true
                    _message.value = body.message
                },
                onFailure = { error ->
                    _state.value = false
                    _message.value = error.message?: "Register Gagal"
                    Log.e("Error Response", error.message.toString())

                }
            )
        }
    }

    fun resetState(){
        _state.value = null
        _message.value = null
    }
}