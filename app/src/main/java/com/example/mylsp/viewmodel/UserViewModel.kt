package com.example.mylsp.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.User
import com.example.mylsp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository):ViewModel(){


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()



    fun getUserByToken(){
        viewModelScope.launch {
            val result = userRepository.getUserByToken()
            result.fold(
                onSuccess = { body->
                    _user.value = body
                    _message.value = "Berhasil di get"
                    Log.d("body", body.toString())
                },
                onFailure = { error->
                    _message.value = error.message?: "Gagal Di ambil"
                    Log.e("body", error.toString())
                }
            )

        }
    }


    fun resetState(){
        _state.value = null
        _message.value = null
    }
}