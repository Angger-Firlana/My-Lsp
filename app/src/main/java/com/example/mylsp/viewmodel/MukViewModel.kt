package com.example.mylsp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lsp24.models.SkemaSertifikasi
import com.example.mylsp.api.APIClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MukViewModel : ViewModel() {

    private val _skemas = MutableStateFlow<List<SkemaSertifikasi>>(emptyList())
    val skemas = _skemas.asStateFlow()

    fun getSkemas() {
        viewModelScope.launch {
            _skemas.value = APIClient.api.getSkemaSertifikasi()
        }
    }
}
