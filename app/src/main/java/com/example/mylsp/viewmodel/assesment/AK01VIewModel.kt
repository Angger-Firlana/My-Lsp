package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.AK01Submission
import com.example.mylsp.model.api.assesment.GetAK01Response
import com.example.mylsp.model.api.assesment.PostApproveRequest
import com.example.mylsp.repository.assesment.AK01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AK01ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AK01Repository(application.applicationContext)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _submission = MutableStateFlow<GetAK01Response?>(null)
    val submission = _submission.asStateFlow()

    fun sendSubmission(aK01SubmissionRequest: AK01Submission) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.sendSubmission(aK01SubmissionRequest)
            result.fold(
                onSuccess = {
                    _state.value = it.success
                    _message.value = it.message
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
                    Log.e("AK01ViewModel", it.message.toString())

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
                    _message.value = "Data berhasil diambil"
                    Log.d("AK01ViewModel", it.toString())
                },
                onFailure = {
                    _submission.value = null
                    _message.value = it.message.toString()
                    Log.e("AK01ViewModel", it.message.toString())
                }
            )
            _loading.value = false
        }
    }

    fun approveAk01(id: Int){
        viewModelScope.launch {
            val result = repository.postApproveAK01(id = id)
            result.fold(
                onSuccess = {
                    _state.value = true
                },
                onFailure = {
                    _state.value = false
                    Log.e("ErrorApproveAk01", it.message?: "Unknown Error")
                }
            )
        }
    }

    fun clearState(){
        _state.value = null
    }
}
