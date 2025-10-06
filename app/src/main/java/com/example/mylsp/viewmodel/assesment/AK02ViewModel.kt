package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.AK02GetSubmission
import com.example.mylsp.model.api.assesment.Ak02Request
import com.example.mylsp.model.api.assesment.Ak02Response
import com.example.mylsp.model.api.assesment.Ak02GetResponse
import com.example.mylsp.repository.assesment.AK02Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AK02ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AK02Repository(application.applicationContext)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _submission = MutableStateFlow<AK02GetSubmission?>(null)
    val submission = _submission.asStateFlow()

    fun postSubmission(request: Ak02Request) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.postAK02(request)
            result.fold(
                onSuccess = {
                    _state.value = it.success
                    _message.value = it.message
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message ?: "Unknown error"
                }
            )
            _loading.value = false
        }
    }

    fun getSubmission(assesiId: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getAk02ByAssesi(assesiId)
            result.fold(
                onSuccess = {
                    _submission.value = it.data?.get(0)
                    _message.value = "Data berhasil diambil"
                    Log.d("AK02Submission", "${it.data}")
                },
                onFailure = {
                    _submission.value = null
                    _message.value = it.message ?: "Unknown error"
                }
            )
            _loading.value = false
        }
    }

    fun updateTtdAsesi(submissionId: Int) {
        viewModelScope.launch {
            val result = repository.patchTtdAsesi(submissionId)
            result.fold(
                onSuccess = {
                    _state.value = true

                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message ?: "Unknown error"
                }
            )
        }
    }

    fun clearState(){
        _state.value = null
        _message.value = ""
    }
}
