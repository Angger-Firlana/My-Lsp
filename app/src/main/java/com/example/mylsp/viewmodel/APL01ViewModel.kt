package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.Asesi
import com.example.mylsp.model.api.FormApl01Data
import com.example.mylsp.repository.APL01Repository
import com.example.mylsp.util.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class APL01ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = APL01Repository(application)

    private val _formData = MutableStateFlow<Asesi?>(null)
    val formData = _formData.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun fetchFormApl01Status() {
        val id = UserManager(application).getUserId()
        viewModelScope.launch {
            val result = repository.getFormApl01Status(id?.toInt() ?: 0)
            result.fold(
                onSuccess = { body ->
                    _formData.value = body
                    Log.d("APL01ViewModel", "Sukses ambil data: ${body}")
                },
                onFailure = { error ->
                    _message.value = error.message.toString()
                    Log.e("APL01ViewModel", "Error ambil APL01: ${_message.value}")
                }
            )
        }
    }

    fun clearData() {
        _formData.value = null
        _message.value = ""
    }
}
