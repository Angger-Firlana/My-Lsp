package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.IA01Request
import com.example.mylsp.model.api.assesment.IA01UnitSubmission
import com.example.mylsp.repository.assesment.Ia01Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IA01ViewModel(application: Application):AndroidViewModel(application) {
    private val repository = Ia01Repository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _submissions = MutableStateFlow<List<IA01UnitSubmission>?>(emptyList())
    val submissions = _submissions.asStateFlow()
    private val _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    fun SendSubmissionIA01(iA01Request: IA01Request){
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
                    Log.e("errorIA01", it.message.toString())
                }
            )
        }
    }

    fun getIA01ByAsesi(asesiId:Int){
        viewModelScope.launch {
            val result = repository.getIA01ByAsesi(asesiId)
            result.fold(
                onSuccess = {
                    _state.value = true
                    _message.value = "Data berhasil diambil"
                    _submissions.value = it.submissions
                },
                onFailure = {
                    _state.value = false
                    _message.value = it.message.toString()
                    _submissions.value = null
                }
            )
        }
    }
}