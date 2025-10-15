package com.example.mylsp.data.api.assesment

import java.io.File

data class PostAnswerRequest(
    val question_id: Int,
    val assesment_asesi_id: Int,
    val files: File
)

data class PostAnswerResponse(
    val status:Boolean,
    val message: String
)

data class GetAnswerResponse(
    val status: Boolean,
    val message: String,
    val data: List<Answer>
)

data class Answer(
    val id:Int,
    val question_id: Int,
    val assesment_asesi_id: Int,
    val files:String
)