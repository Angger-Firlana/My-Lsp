package com.example.mylsp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.assesment.Assessment
import com.example.mylsp.repository.assesment.AssesmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssesmentViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AssesmentRepository(application.applicationContext)

    private val _listAssesment = MutableStateFlow<List<com.example.mylsp.data.api.assesment.Assessment>>(emptyList())
    val listAssessment = _listAssesment.asStateFlow()

    private val _assesment = MutableStateFlow<com.example.mylsp.data.api.assesment.Assessment?>(null)
    val assesment = _assesment.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getListAssesmentByUser(userId: Int){
        viewModelScope.launch {
            val result = repository.getAssesments()
            result.fold(
                onSuccess = {
                    _listAssesment.value = it.data.filter { listA->
                        listA.assesor.user_id == userId
                    }
                },
                onFailure = {
                    _message.value = it.message.toString()
                }
            )
        }
    }

    fun getAssesmentById(id:Int){
        viewModelScope.launch {
            val result = repository.getAssesmentById(id)
            result.fold(
                onSuccess = {
                    _assesment.value = it.data
                },
                onFailure = {
                    _message.value = it.message.toString()
                    Log.e("Error Log", it.message.toString())
                }

            )
        }
    }

    fun getListAssesment(){
        viewModelScope.launch {
            val result = repository.getAssesments()
            result.fold(
                onSuccess = {
                    _listAssesment.value = it.data
                },
                onFailure = {
                    _message.value = it.message.toString()
                }
            )
        }
    }
}