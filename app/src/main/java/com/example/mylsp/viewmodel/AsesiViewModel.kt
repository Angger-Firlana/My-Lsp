package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.repository.AsesiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AsesiViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AsesiRepository(application)

    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

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

    fun resetState(){
        _state.value = null
        _message.value = ""

    }
}