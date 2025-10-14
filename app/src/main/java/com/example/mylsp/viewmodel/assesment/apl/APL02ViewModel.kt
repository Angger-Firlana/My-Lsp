package com.example.mylsp.viewmodel.assesment.apl

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.assesment.DataApl02
import com.example.mylsp.data.api.assesment.GetAPL02Response
import com.example.mylsp.data.api.assesment.PostApproveRequest
import com.example.mylsp.data.repository.assesment.APL02Repository
import com.example.mylsp.util.Util
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class APL02ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = APL02Repository(application)

    private val _apl02 = MutableStateFlow<DataApl02?>(null)
    val apl02 = _apl02.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _apl02Submission = MutableStateFlow<GetAPL02Response?>(null)
    val apl02Submission = _apl02Submission.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // Flag untuk track jika sedang loading submission
    private val _isLoadingSubmission = MutableStateFlow(false)
    val isLoadingSubmission = _isLoadingSubmission.asStateFlow()

    fun getAPL02(idSchemas: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getApl02(idSchemas)
                result.fold(
                    onSuccess = { body ->
                        _apl02.value = body.data
                        Log.d("APL02_VM", "getAPL02 Success: ${body.data}")
                    },
                    onFailure = { error ->
                        _message.value = error.message ?: "Unknown Error"
                        Log.e("APL02_VM", "getAPL02 Error: ${error.message}")
                    }
                )
            } catch (e: Exception) {
                _message.value = e.message ?: "Unknown Error"
                Log.e("APL02_VM", "getAPL02 Exception: ${e.message}")
            }
        }
    }

    fun sendApl02() {
        viewModelScope.launch {
            try {
                val result = repository.sendSubmission(Util.jawabanApl02.value)
                result.fold(
                    onSuccess = {
                        _state.value = true
                        Log.d("APL02_VM", "sendApl02 Success")
                    },
                    onFailure = {
                        _state.value = false
                        Log.e("Error APL 02", it.message ?: "gajelas")
                        _message.value = it.message ?: "Unknown Error"
                    }
                )
            } catch (e: Exception) {
                _state.value = false
                Log.e("Error APL 02", e.message ?: "gajelas")
                _message.value = e.message ?: "Unknown Error"
            }
        }
    }

    fun approveApl02(assesiId: Int, approveReq: PostApproveRequest) {
        viewModelScope.launch {
            try {
                val result = repository.approveApl02(assesiId, approveReq)
                result.fold(
                    onSuccess = {
                        _state.value = true
                        Log.d("APL02_VM", "approveApl02 Success")
                    },
                    onFailure = {
                        _state.value = false
                        _message.value = it.message ?: "Unknown Error"
                        Log.e("Error Approve Apl02", it.message ?: "gajelas")
                    }
                )
            } catch (e: Exception) {
                _state.value = false
                _message.value = e.message ?: "Unknown Error"
                Log.e("Error Approve Apl02", e.message ?: "gajelas")
            }
        }
    }

    fun getSubmissionByAsesi(asesiId: Int) {
        viewModelScope.launch {
            try {
                _isLoadingSubmission.value = true
                Log.d("APL02_VM", "getSubmissionByAsesi: Loading submission for asesiId=$asesiId")

                val result = repository.getSubmissionByAsesi(asesiId)
                result.fold(
                    onSuccess = { body ->
                        // JANGAN set ke null, update state dengan data yang ada
                        _apl02Submission.value = body
                        Log.d("APL02_VM", "getSubmissionByAsesi Success: ${body?.data?.size} units")
                    },
                    onFailure = { error ->
                        // PENTING: Jangan overwrite submission yang sudah ada!
                        // Cukup log error dan set message
                        _message.value = error.message ?: "Unknown Error"
                        Log.e("APL02_VM", "getSubmissionByAsesi Error: ${error.message}")

                        // Jika submission sudah ada, pertahankan
                        if (_apl02Submission.value == null) {
                            Log.d("APL02_VM", "No cached submission data")
                        } else {
                            Log.d("APL02_VM", "Using cached submission data")
                        }
                    }
                )
            } catch (e: Exception) {
                _message.value = e.message ?: "Unknown Error"
                Log.e("APL02_VM", "getSubmissionByAsesi Exception: ${e.message}")
            } finally {
                _isLoadingSubmission.value = false
            }
        }
    }

    fun resetState() {
        _state.value = null
    }

    fun clearSubmission() {
        _apl02Submission.value = null
        _message.value = ""
    }
}