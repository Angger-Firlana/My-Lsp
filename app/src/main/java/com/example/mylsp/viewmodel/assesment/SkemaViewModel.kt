package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.model.api.SkemaDetail
import com.example.mylsp.repository.assesment.SkemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SkemaViewModel(application: Application):AndroidViewModel(application) {
    private val repository = SkemaRepository(application.applicationContext)

    private val _skemas = MutableStateFlow<List<com.example.mylsp.data.model.api.SkemaDetail>>(emptyList())
    val skemas = _skemas.asStateFlow()

    fun getListSkema() {
        viewModelScope.launch {
            val result = repository.getListSkema()
            result.fold(
                onSuccess = { response ->
                    _skemas.value = response.data
                },
                onFailure = { throwable ->
                    Log.e("ViewModel", "Error getListSkema: ${throwable.message}")
                }
            )
        }
    }

}