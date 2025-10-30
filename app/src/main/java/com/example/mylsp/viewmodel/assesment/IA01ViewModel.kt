package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.assesment.IA01Detail
import com.example.mylsp.data.api.assesment.IA01GetData
import com.example.mylsp.data.api.assesment.IA01Request
import com.example.mylsp.data.repository.assesment.Ia01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IA01ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Ia01Repository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _stateApproved = MutableStateFlow<Boolean?>(null)
    val stateApproved = _stateApproved.asStateFlow()

    // Ubah struktur: List<IA01Detail> (flat list dari semua details)
    private val _submissions = MutableStateFlow<com.example.mylsp.data.api.assesment.IA01GetData?>(null)
    val submissions = _submissions.asStateFlow()

    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    fun SendSubmissionIA01(iA01Request: com.example.mylsp.data.api.assesment.IA01Request) {
        viewModelScope.launch {
            val result = repository.postIa01(iA01Request)
            result.fold(
                onSuccess = {
                    _state.value = true
                    _message.value = it.message
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
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
                    } else {
                        _submissions.value = null
                        _message.value = "Tidak ada data submission"
                    }
                },
                onFailure = { exception ->
                    _submissions.value = null
                    _message.value = exception.message ?: "Error mengambil data"
                }
            )
        }
    }

    fun approveIa01ByAsesi(id:Int){
        viewModelScope.launch {
            val result = repository.postApproveAsesi(id)
            result.fold(
                onSuccess = {
                    _stateApproved.value = true
                },
                onFailure = {
                    _stateApproved.value = false
                }
            )
        }
    }

    fun resetState() {
        _state.value = null
        _stateApproved.value = null
    }

    fun clearSubmissions() {
        _submissions.value = null
    }
}