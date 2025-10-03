package com.example.mylsp.model.api.assesment

data class GetAK04Response(
    val success: Boolean,
    val message: String,
    val data: List<AK04>
)
data class AK04(
    val id:Int? = null,
    val assesment_asesi_id: Int,
    val alasan_banding: String,
    val questions: List<SubmissionQuestion>
)

data class SubmissionQuestion(
    val ak04_question_id: Int,
    val selected_option: String
)
data class PostAK04Response(
    val success: Boolean,
    val message: String,
    val submission_id: Int
)
data class GetAK04QuestionResponse(
    val success: Boolean,
    val message: String,
    val data: List<AK04Question>
)
data class AK04Question(
    val id:Int,
    val question: String
)