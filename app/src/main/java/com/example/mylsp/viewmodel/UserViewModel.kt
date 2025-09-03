package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.User
import com.example.mylsp.repository.UserRepository
import com.example.mylsp.util.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val application: Application):AndroidViewModel(application){
    private val userManager = UserManager(application.applicationContext)
    private val userRepository = UserRepository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun getUserByToken(){
        viewModelScope.launch {
            val result = userRepository.getUserByToken()
            result.fold(
                onSuccess = { body->
                    val user = body.user
                    Log.d("UserViewModel", "getUserByToken: $user")
                    _message.value = "Berhasil di get"
                    userManager.saveUser(
                        id = user.id.toString(),
                        name = user.username,
                        email = user.email,
                        role = user.role,
                        jurusan_id = user.jurusan_id
                    )
                },
                onFailure = { error->
                    _message.value = error.message?: "Gagal Di ambil"
                }
            )

        }
    }


    fun resetState(){
        _state.value = null
        _message.value = null
    }
}