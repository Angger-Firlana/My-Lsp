package com.example.mylsp.viewmodel.assesment

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylsp.data.api.assesment.Answer
import com.example.mylsp.data.api.assesment.PostAnswerRequest
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

    private val _listAnswer = MutableStateFlow<List<Answer>>(emptyList())
    val listAnswer = _listAnswer.asStateFlow()

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

    var currentDownloadId: Int = 0

    fun downloadQuestionToUri(context: Context, id: Int, uri: Uri) {
        viewModelScope.launch {
            val result = repository.downloadQuestionToUri(id, context, uri)
            result.fold(
                onSuccess = {
                    Log.d("Download", "File berhasil disimpan ke $uri")
                },
                onFailure = { e ->
                    Log.e("Download", "Gagal menyimpan file: ${e.message}")
                }
            )
        }
    }

    fun downloadAnswerToUri(context: Context, id: Int, uri: Uri) {
        viewModelScope.launch {
            val result = repository.downloadAnswerToUri(id, context, uri)
            result.fold(
                onSuccess = {
                    Log.d("Download", "File berhasil disimpan ke $uri")
                },
                onFailure = { e ->
                    Log.e("Download", "Gagal menyimpan file: ${e.message}")
                }
            )
        }
    }


    fun uploadQuestion(request: PostAnswerRequest){
        viewModelScope.launch {
            val result = repository.uploadAnswer(postAnswerRequest = request)
            result.fold(
                onSuccess = {
                    _state.value = true
                }, onFailure = {
                    _state.value = false
                }
            )

        }
    }

    fun getAnswer(){
        viewModelScope.launch {
            val result = repository.getAnswer()
            result.fold(
                onSuccess = { body ->
                    _listAnswer.value = body.data
                },
                onFailure = {
                    Log.e("GetAnswerError", it.message.toString())
                }
            )
        }
    }



    fun clearState(){
        _state.value = null
    }

}