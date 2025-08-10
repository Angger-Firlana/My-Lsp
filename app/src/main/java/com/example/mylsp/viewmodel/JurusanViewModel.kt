package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.Jurusan
import com.example.mylsp.repository.JurusanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JurusanViewModel(application:Application):AndroidViewModel(application) {
    private val repository = JurusanRepository(application)

    private val _jurusans = MutableStateFlow<List<Jurusan>>(emptyList())
    val jurusans = _jurusans.asStateFlow()

    private val _message= MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getJurusans(){
        viewModelScope.launch {
            val result = repository.getJurusans()
            result.fold(
                onSuccess = { body ->
                    _jurusans.value = body
                },
                onFailure = { error->
                    _message.value = error.message.toString()
                    Log.e("error", _message.value)
                }
            )
        }
    }
}