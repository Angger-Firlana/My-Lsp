package com.example.mylsp.model.api.assesment

import okhttp3.MultipartBody

data class AK01SubmissionRequest(
    val assesmentAsesiId: Int,
    val attachments: List<MultipartBody.Part>
)

data class AK01SubmissionResponse(
    val message: String,
    val success: Boolean
)