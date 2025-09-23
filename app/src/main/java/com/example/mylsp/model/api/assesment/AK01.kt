package com.example.mylsp.model.api.assesment

import com.google.gson.annotations.SerializedName

data class AK01Submission(
    @SerializedName("assesment_asesi_id")
    val assesmentAsesiId: Int,
    val attachments: List<AttachmentAk01>
)

data class GetAK01Response(
    val message: String,
    val success: Boolean,
    val data: AK01Submission? = null
)

data class AttachmentAk01(
    val description: String
)

data class AK01SubmissionResponse(
    val message: String,
    val success: Boolean
)