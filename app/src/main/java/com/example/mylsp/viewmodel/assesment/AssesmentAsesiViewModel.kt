package com.example.mylsp.viewmodel.assesment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.model.api.Asesi
import com.example.mylsp.model.api.asesi.AssesmentAsesi
import com.example.mylsp.model.api.asesi.PostAssesmentAsesiReq
import com.example.mylsp.repository.assesment.AssesmentAsesiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssesmentAsesiViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AssesmentAsesiRepository(application.applicationContext)

    private val _listAsesiAssesment = MutableStateFlow<List<AssesmentAsesi>>(emptyList())
    val listAsesi = _listAsesiAssesment.asStateFlow()

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

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

    fun daftarAssesment(assesmentId: Int, assesiId:Int){
        viewModelScope.launch {
            val assesmentAsesiReq = PostAssesmentAsesiReq(assesmentId, assesiId)
            val result = repository.postAssesmentAsesi(
                assesmentAsesiReq
            )

            result.fold(
                onSuccess = {
                    _state.value = true

                },
                onFailure = {
                    _state.value = false
                }
            )
        }
    }

    fun clearState(){
        _state.value = null
    }
}