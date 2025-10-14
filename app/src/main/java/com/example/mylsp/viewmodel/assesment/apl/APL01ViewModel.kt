package com.example.mylsp.viewmodel.assesment.apl

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.data.model.api.Apl01
import com.example.mylsp.data.repository.assesment.APL01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class APL01ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = APL01Repository(application)

    private val _formData = MutableStateFlow<Apl01?>(null)
    val formData = _formData.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading = _loading.asStateFlow()

    fun fetchFormApl01Status() {
        val id = UserManager(application).getUserId()
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getFormApl01Status(id?.toInt() ?: 0)
            result.fold(
                onSuccess = { body ->
                    _loading.value = false
                    _formData.value = body
                    Log.d("APL01ViewModel", "Sukses ambil data: ${body}")
                },
                onFailure = { error ->
                    _loading.value = false
                    _message.value = error.message.toString()
                    Log.e("APL01ViewModel", "Error ambil APL01: ${_message.value}")
                }
            )
        }
    }

    fun clearState() {
        _loading.value = null
        _message.value = ""
    }
}
