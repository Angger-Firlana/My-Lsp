package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.FormApl01Data
import com.example.mylsp.repository.APL01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class APL01ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = APL01Repository(application)

    private val _formData = MutableStateFlow<FormApl01Data?>(null)
    val formData = _formData.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun fetchFormApl01Status() {
        viewModelScope.launch {
            val result = repository.getFormApl01Status()
            result.fold(
                onSuccess = { body ->
                    _formData.value = body.data
                    _message.value = body.message
                    Log.d("APL01ViewModel", "Sukses ambil data: ${body.data}")
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
