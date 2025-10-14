package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.data.model.api.Asesi
import com.example.mylsp.data.model.api.AsesiRequest
import com.example.mylsp.data.repository.auth.AsesiRepository
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

    private val _asesis = MutableStateFlow<List<Asesi>>(emptyList())
    val asesis = _asesis.asStateFlow()

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

    fun getDataAsesis(){
        viewModelScope.launch {
            val result = repository.getDataAsesis()
            result.fold(
                onSuccess ={ body ->
                    _asesis.value = body.data
                },
                onFailure = { error ->
                    _message.value = error.message?: "Error Unknown"
                }
            )
        }
    }

    fun getDataAsesiByUser(id:Int){
        viewModelScope.launch {
            val result = repository.getDataAsesis()
            result.fold(
                onSuccess ={ body ->
                    _asesi.value = body.data.find { it.user_id == id}
                },
                onFailure = { error ->
                    _message.value = error.message?: "Error Unknown"
                }
            )
        }
    }

    fun updateDataAsesi(id:Int,asesiRequest: AsesiRequest){
        viewModelScope.launch {
            val result = repository.updateDataAsesi(id,asesiRequest)
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