package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.Asesi
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.repository.auth.AsesiRepository
import com.example.mylsp.util.user.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AsesiViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AsesiRepository(application)
    private val userManager = UserManager(application)

    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _asesi = MutableStateFlow<Asesi?>(null)
    val asesi = _asesi.asStateFlow()

    fun createDataAsesi(asesiRequest: AsesiRequest){
        viewModelScope.launch {
            val result = repository.createDataAsesi(asesiRequest)
            result.fold(
                onSuccess = { body ->
                    _state.value = true
                    _message.value = body.message
                },
                onFailure = { error ->
                    _state.value = false
                    _message.value = error.message?: "Unknown error"
                    Log.e("Error", _message.value)
                }
            )
        }
    }

    fun getDataAsesi(){
        viewModelScope.launch {
            val result = repository.getDataAsesi()
            result.fold(
                onSuccess ={ body ->
                    _asesi.value = body
                },
                onFailure = { error ->
                    _message.value = error.message?: "Error Unknown"
                }
            )
        }
    }

    fun getDataApl01ByUser(){
        viewModelScope.launch {
            val id = userManager.getUserId()?.toInt()?: 0
            val result = repository.getApl01ByUser(id)
            result.fold(
                onSuccess = { body ->
                    _asesi.value = body
                },
                onFailure = { error ->
                    _message.value = error.message?: "Error Unknown"
                }
            )
        }
    }

    fun resetState(){
        _state.value = null
        _message.value = ""

    }
}