package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.LoginRequest
import com.example.mylsp.model.api.RegisterRequest
import com.example.mylsp.repository.AuthRepository
import com.example.mylsp.repository.UserRepository
import com.example.mylsp.util.TokenManager
import com.example.mylsp.util.UserManager
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
                    tokenManager.saveToken(body.token)
                },
                onFailure = { error ->
                    _state.value = false
                    _message.value = error.message ?: "Login Gagal : Tidak Diketahui"
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