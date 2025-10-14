package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.data.api.asesi.AssesmentAsesi
import com.example.mylsp.data.api.asesi.PatchStatusReq
import com.example.mylsp.data.api.asesi.PostAssesmentAsesiReq
import com.example.mylsp.data.repository.assesment.AssesmentAsesiRepository
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssesmentAsesiViewModel(application: Application):AndroidViewModel(application) {
    private val repository =
        com.example.mylsp.data.repository.assesment.AssesmentAsesiRepository(application.applicationContext)
    val assesmentAsesiManager = AssesmentAsesiManager(application.applicationContext)
    private val _listAsesiAssesment = MutableStateFlow<List<AssesmentAsesi>>(emptyList())
    val listAssesmentAsesi = _listAsesiAssesment.asStateFlow()

    private val _assesmentAsesi = MutableStateFlow<AssesmentAsesi?>(null)
    val assesmentAsesi = _assesmentAsesi.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun getListAsesiByAssesment(assesmentId:Int){
        viewModelScope.launch {
            val result = repository.getAssesmentAsesiByAssesment(assesmentId)
            result.fold(
                onSuccess = {
                    _listAsesiAssesment.value = it.data

                },
                onFailure = {

                }
            )
        }
    }

    fun getAssesmentAsesiByAsesi(asesiId:Int){
        viewModelScope.launch {
            val result = repository.getAssesmentAsesiByAsesi(asesiId)
            result.fold(
                onSuccess = { response ->
                    _assesmentAsesi.value = response.data[0]
                    Log.d("AssesmentAsesiViewModel", response.data[0].toString())
                },
                onFailure = {
                    Log.e("AssesmentAsesiViewModel", "Error: ${it.message}")

                }
            )
        }
    }

    fun daftarAssesment(assesmentId: Int, assesiId:Int){
        viewModelScope.launch {
            val assesmentAsesiReq =
                PostAssesmentAsesiReq(assesmentId, assesiId)
            val result = repository.postAssesmentAsesi(
                assesmentAsesiReq
            )

            result.fold(
                onSuccess = {body->
                    _state.value = true
                    body.data?.let {
                        _state.value = true
                        assesmentAsesiManager.saveAssesmentAsesi(it)
                    }?:run {
                        _state.value = false
                    }
                },
                onFailure = {
                    _state.value = false
                    Log.e("AssesmentAsesiViewModel", "Error: ${it.message}")
                }
            )
        }
    }
    fun updateStatusAssesmentAsesi(assesmentAsesiId:Int, status:String){
        viewModelScope.launch {
            _loading.value = true
            val request = PatchStatusReq(status)
            val result = repository.patchStatus(assesmentAsesiId,request)
            result.fold(
                onSuccess = {
                    _loading.value = false
                    _state.value = true
                },
                onFailure = {
                    _loading.value = false
                    _state.value = false
                    _message.value = it.message.toString()
                }
            )
        }
    }

    fun deleteAssesmentAsesi(id:Int){

    }

    fun clearState(){
        _state.value = null
    }
}