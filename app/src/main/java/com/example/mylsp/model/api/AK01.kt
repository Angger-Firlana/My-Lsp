package com.example.mylsp.model.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class AK01SubmissionRequest(
    val assesmentAsesiId: Int,
    val attachments: List<MultipartBody.Part>
)

data class AK01SubmissionResponse(
    val message: String,
    val success: Boolean
)