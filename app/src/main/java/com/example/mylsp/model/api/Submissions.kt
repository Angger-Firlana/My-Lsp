package com.example.mylsp.model.api

data class SubmissionGroup(
    val skema_id: Int,
    var submissions: List<Submission>
)

data class Submission(
    val unit_ke: Int,
    val kode_unit: String,
    var elemen: List<ElemenSubmission>
)

data class ElemenSubmission(
    val elemen_id: Int, // Ganti dari elemen_index
    val kompetensinitas: String, // Typo diperbaiki biar sama
    val bukti_yang_relevan: List<BuktiSubmission>
)

data class BuktiSubmission(
    val bukti_description: String
)

data class ResponseSubmission(
    val success: Boolean,
    val message: String,
    val submission_id: Int
)
