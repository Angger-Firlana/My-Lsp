package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.AK04
import com.example.mylsp.model.api.assesment.GetAK04Response
import com.example.mylsp.model.api.assesment.GetAK04QuestionResponse
import com.example.mylsp.repository.assesment.Ak04Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Ak04ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Ak04Repository(application.applicationContext)

    // state submit
    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    // message response
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // data ak04 (submissions)
    private val _submissions = MutableStateFlow<List<AK04>?>(null)
    val submissions = _submissions.asStateFlow()

    // pertanyaan
    private val _questions = MutableStateFlow<GetAK04QuestionResponse?>(null)
    val questions = _questions.asStateFlow()

    fun sendSubmissionAk04(request: AK04) {
        viewModelScope.launch {
            val result = repository.postAk04(request)
            result.fold(
                onSuccess = {
                    _state.value = true
                    _message.value = it.message
                    Log.d("Ak04ViewModel", "Success: ${it.message}")
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message ?: "Error mengirim AK04"
                    Log.e("Ak04ViewModel", "Error: ${it.message}")
                }
            )
        }
    }

    fun getAk04ByAsesi(asesiId: Int) {
        viewModelScope.launch {
            val result = repository.getAk04ByAsesi(asesiId)
            result.fold(
                onSuccess = { response: GetAK04Response ->
                    if (response.data.isNotEmpty()) {
                        _submissions.value = response.data
                        _message.value = "Data berhasil diambil"
                        Log.d("Ak04ViewModel", "Loaded ${response.data.size} submissions")
                    } else {
                        _submissions.value = null
                        _message.value = "Tidak ada data submission"
                        Log.d("Ak04ViewModel", "No submissions found")
                    }
                },
                onFailure = { exception ->
                    _submissions.value = null
                    _message.value = exception.message ?: "Error mengambil data"
                    Log.e("Ak04ViewModel", "Error: ${exception.message}")
                }
            )
        }
    }

    fun getAk04Questions() {
        viewModelScope.launch {
            val result = repository.getAk04Questions()
            result.fold(
                onSuccess = { response ->
                    _questions.value = response
                    _message.value = "Pertanyaan berhasil diambil"
                    Log.d("Ak04ViewModel", "Questions loaded: ${response.data.size}")
                },
                onFailure = { exception ->
                    _questions.value = null
                    _message.value = exception.message ?: "Error mengambil pertanyaan"
                    Log.e("Ak04ViewModel", "Error: ${exception.message}")
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

    fun clearQuestions() {
        _questions.value = null
    }
}
