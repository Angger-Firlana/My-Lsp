package com.example.mylsp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.Apl02
import com.example.mylsp.repository.APL02Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class APL02ViewModel(application: Application):AndroidViewModel(application) {
    private val repository = APL02Repository(application)

    private val _apl02 = MutableStateFlow<Apl02?>(null)
    val apl02 = _apl02.asStateFlow()

    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()
    fun getAPL02(idSchemas:Int){
        viewModelScope.launch {
            val result = repository.getApl02(idSchemas)
            result.fold(
                onSuccess = { body ->
                    _apl02.value = body
                    _message.value = "Apl 02 Berhasil diambil"
                },
                onFailure = { error ->
                    _message.value = error.message?:"Unknown Error"
                }
            )
        }
    }
}