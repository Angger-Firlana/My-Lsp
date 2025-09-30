package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.IA01Detail
import com.example.mylsp.model.api.assesment.IA01GetData
import com.example.mylsp.model.api.assesment.IA01Request
import com.example.mylsp.repository.assesment.Ia01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IA01ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Ia01Repository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    // Ubah struktur: List<IA01Detail> (flat list dari semua details)
    private val _submissions = MutableStateFlow<IA01GetData?>(null)
    val submissions = _submissions.asStateFlow()

    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    fun SendSubmissionIA01(iA01Request: IA01Request) {
        viewModelScope.launch {
            val result = repository.postIa01(iA01Request)
            result.fold(
                onSuccess = {
                    _state.value = true
                    _message.value = it.message
                    Log.d("IA01ViewModel", "Success: ${it.message}")
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
                    Log.e("IA01ViewModel", "Error: ${it.message}")
                }
            )
        }
    }

    fun getIA01ByAsesi(asesiId: Int) {
        viewModelScope.launch {
            val result = repository.getIA01ByAsesi(asesiId)
            result.fold(
                onSuccess = { response ->
                    if (response.data.isNotEmpty()) {
                        // Ambil details dari submission pertama
                        // Jika ada multiple submissions, kamu bisa adjust logic di sini
                        _submissions.value = response.data[0]
                        _message.value = "Data berhasil diambil"
                        Log.d("IA01ViewModel", "Loaded ${response.data[0].details.size} submissions")
                    } else {
                        _submissions.value = null
                        _message.value = "Tidak ada data submission"
                        Log.d("IA01ViewModel", "No submissions found")
                    }
                },
                onFailure = { exception ->
                    _submissions.value = null
                    _message.value = exception.message ?: "Error mengambil data"
                    Log.e("IA01ViewModel", "Error: ${exception.message}")
                }
            )
        }
    }

    fun resetState() {
        _state.value = null
    }

    fun clearSubmissions() {
        _submissions.value = null
    }
}