package com.example.mylsp.viewmodel.assesment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.assesment.KomponenData
import com.example.mylsp.data.repository.assesment.KomponenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KomponenViewModel(application:Application):AndroidViewModel(application) {
    private val repository = KomponenRepository(application.applicationContext)
    private val _listKomponen = MutableStateFlow<List<com.example.mylsp.data.api.assesment.KomponenData>>(emptyList())
    val listKomponen = _listKomponen.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getListKomponen(){
        viewModelScope.launch {
            val result = repository.getKomponens()
            result.fold(
                onSuccess = { body ->
                    _listKomponen.value = body.data
                },
                onFailure = {
                    _message.value = it.message?: "Unknown Error"
                }
            )
        }
    }
}