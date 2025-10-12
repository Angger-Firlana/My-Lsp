package com.example.mylsp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.remote.api.APIClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MukViewModel : ViewModel() {

    private val _skemas = MutableStateFlow<List<String>>(emptyList())
    val skemas = _skemas.asStateFlow()


}
