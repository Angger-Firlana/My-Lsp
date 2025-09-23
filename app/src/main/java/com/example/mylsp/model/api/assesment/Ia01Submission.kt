package com.example.mylsp.model.api.assesment

// Root request
data class IA01Request(
    val assesment_asesi_id: Int,
    val submissions: List<IA01UnitSubmission>
)

// Response untuk GET
data class IA01GetResponse(
    val success: Boolean,
    val message: String,
    val submissions: List<IA01UnitSubmission>
)

// Unit level submission
data class IA01UnitSubmission(
    val unit_ke: Int,
    val kode_unit: String,
    val elemen: List<IA01ElemenSubmission>
)

// Elemen level
data class IA01ElemenSubmission(
    val elemen_id: Int,
    val kuk: List<IA01KUKSubmission>
)

// KUK level
data class IA01KUKSubmission(
    val kuk_id: Int,
    val skkni: String, // "ya" atau "tidak"
    val penilaian_lanjut: List<IA01PenilaianLanjut>
)

// Penilaian lanjut per KUK
data class IA01PenilaianLanjut(
    val teks_penilaian: String
)

// Response untuk POST
data class IA01Response(
    val success: Boolean,
    val message: String,
    val submission_id: Int
)
