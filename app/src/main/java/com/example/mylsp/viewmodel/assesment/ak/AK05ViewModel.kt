package com.example.mylsp.viewmodel.assesment.ak

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.data.api.assesment.GetAk05Response
import com.example.mylsp.data.repository.assesment.Ak05Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AK05ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Ak05Repository(application.applicationContext)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _submission = MutableStateFlow<GetAk05Response?>(null)
    val submission = _submission.asStateFlow()

    fun sendSubmission(request: Ak05SubmissionRequest) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.sendSubmission(request)
            result.fold(
                onSuccess = {
                    _state.value = it.success
                    _message.value = it.message
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
                    Log.e("Ak05ViewModel", it.message.toString())
                }
            )
            _loading.value = false
        }
    }

    fun getSubmission(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getSubmission(id)
            result.fold(
                onSuccess = {
                    _submission.value = it
                    _state.value = true
                    _message.value = "Data berhasil diambil"
                },
                onFailure = {
                    _submission.value = null
                    _state.value = false
                    _message.value = it.message.toString()
                    Log.e("Ak05ViewModel", it.message.toString())
                }
            )
            _loading.value = false
        }
    }
}
