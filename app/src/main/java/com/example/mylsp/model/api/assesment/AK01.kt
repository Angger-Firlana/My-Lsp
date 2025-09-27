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
    val data: List<AK01>? = null
)

data class AK01(
    val id:Int,
    @SerializedName("assesment_asesi_id")
    val assesmentAsesiId: Int,
    val ttd_asesi:Int,
    val ttd_assesor:Int,
    val status: String,
    val submission_date: String,
    val attachments: List<AttachmentAk01>
)

data class AttachmentAk01(
    val description: String,
    val id: Int? = null,
    val submission_id: Int? = null
)

data class AK01SubmissionResponse(
    val message: String,
    val success: Boolean
)