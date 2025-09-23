package com.example.mylsp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.assesment.Assessment
import com.example.mylsp.repository.assesment.AssesmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssesmentViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AssesmentRepository(application.applicationContext)

    private val _listAssesment = MutableStateFlow<List<Assessment>>(emptyList())
    val listAssessment = _listAssesment.asStateFlow()

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