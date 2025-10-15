package com.example.mylsp.data.api.assesment


data class Question(
    val id:Int,
    val skema_id:Int,
    val file_path:String
)

data class QuestionResponse(
    val status: Boolean,
    val message:String,
    val data:List<Question>
)

