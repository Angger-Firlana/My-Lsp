package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.GetAK03Data
import com.example.mylsp.model.api.assesment.PostAK03Request
import com.example.mylsp.repository.assesment.AK03Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AK03ViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AK03Repository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _submissions = MutableStateFlow<List<GetAK03Data>?>(null)
    val submissions = _submissions.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()


    fun sendSubmissionAK03(bodyRequest: PostAK03Request){
        viewModelScope.launch {
            val result = repository.postAK03(bodyRequest)
            result.fold(
                onSuccess = {
                    _state.value = true
                },
                onFailure = {
                    _state.value = false
                    Log.e("ErrorAK03ViewModel", it.message?: "Unknown")
                    _message.value = it.message?: "Unknown"
                }
            )
        }
    }

    fun getAK03ByAsesi(id:Int){
        viewModelScope.launch {
            val result = repository.getAK03ByAsesi(id)

            result.fold(
                onSuccess = { body ->
                    Log.d("AK03ViewModel", body.data[0].toString())
                    _submissions.value = body.data

                },
                onFailure = {
                    _message.value = it.message?: "Unknown"
                    Log.e("ErrorAK03ViewModel", it.message?: "Unknown")
                }
            )
        }
    }

    fun resetState(){
        _message.value = ""
        _state.value = null
    }
}