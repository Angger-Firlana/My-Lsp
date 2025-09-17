package com.example.mylsp.model.api.assesment

// Root request
data class IA01Request(
    val skema_id: Int,
    val assesment_asesi_id: Int,
    val submissions: List<Ia01Submission>
)

// Submission level
data class Ia01Submission(
    val unit_ke: Int,
    val kode_unit: String,
    val elemen: List<Elemen>
)

// Elemen level
data class Elemen(
    val elemen_id: Int,
    val kuk: List<KUK>
)

// KUK level
data class KUK(
    val kuk_id: Int,
    val skkni: String,
    val penilaian_lanjut: List<PenilaianLanjut>
)

// Penilaian lanjut level
data class PenilaianLanjut(
    val teks_penilaian: String
)

// Response
data class IA01Response(
    val success: Boolean,
    val message: String,
    val submission_id: Int
)

