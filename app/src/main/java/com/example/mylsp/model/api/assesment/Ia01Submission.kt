package com.example.mylsp.model.api.assesment

// Root request
data class IA01Request(
    val skema_id: Int,
    val assesment_asesi_id: Int,
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

// KUK level - sesuai untuk observasi IA01
data class IA01KUKSubmission(
    val kuk_id: Int,
    val hasil_observasi: String, // "Kompeten" atau "Belum Kompeten"
    val catatan_asesor: String   // catatan observasi asesor
)

// Response
data class IA01Response(
    val success: Boolean,
    val message: String,
    val submission_id: Int
)

