package com.example.mylsp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.AK01SubmissionRequest
import com.example.mylsp.repository.assesment.AK01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AK01VIewModel(application: Application):AndroidViewModel(application) {
    private val repository = AK01Repository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun sendSubmission(aK01SubmissionRequest: AK01SubmissionRequest){
        viewModelScope.launch {
            val result = repository.sendSubmission(aK01SubmissionRequest)
            result.fold(
                onSuccess = {
                    _state.value = it.success
                    _message.value = it.message
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
                }
            )
        }
    }
}