package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylsp.data.api.assesment.Question
import com.example.mylsp.data.repository.assesment.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application):AndroidViewModel(application) {
    private val repository = QuestionRepository(application.applicationContext)

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    private val _listQuestion = MutableStateFlow<List<Question>>(emptyList())
    val listQuestion = _listQuestion.asStateFlow()

    fun getQuestionBySkema(id:Int){
        viewModelScope.launch {
            val result = repository.getQuestionBySkema(id)
            result.fold(
                onSuccess = { body ->
                    _listQuestion.value = body.data
                },
                onFailure = {
                    Log.e("Question View Model", it.message.toString())
                }
            )
        }
    }

    fun downloadQuestion(id: Int, savePath: String) {
        viewModelScope.launch {
            val result = repository.downloadQuestion(id, savePath)
            result.fold(
                onSuccess = { file ->
                    _state.value = true
                    Log.d("Download", "File tersimpan di: ${file.absolutePath}")
                },
                onFailure = { error ->
                    _state.value = false
                    Log.e("QuestionViewModel", error.message ?: "Unknown error")
                }
            )
        }
    }

}