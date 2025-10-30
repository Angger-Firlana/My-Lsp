package com.example.mylsp.data.api.assesment

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.math.IntMath

// Root request
data class IA01Request(
    val assesment_asesi_id: Int,
    val submissions: List<com.example.mylsp.data.api.assesment.IA01UnitSubmission>
)

// Response untuk GET
data class IA01GetResponse(
    val success: Boolean,
    val message: String,
    val data: List<com.example.mylsp.data.api.assesment.IA01GetData>
)

data class IA01GetData(
    val id: Int,
    val ttd_asesi: Int,
    val ttd_asesor:Int,
    val submission_date: String,
    val assesment_asesi_id: Int,
    val details: List<com.example.mylsp.data.api.assesment.IA01Detail>
)

data class IA01Detail(
    val unit_ke: Int,
    val kode_unit: String,
    val elemen_id: Int,
    val kuk_id:Int,
    val skkni: String,
    val teks_penilaian: String,
    val element: ElemenAPL02
)

// Unit level submission
data class IA01UnitSubmission(
    val unit_ke: Int,
    val kode_unit: String,
    val elemen: List<com.example.mylsp.data.api.assesment.IA01ElemenSubmission>

)

// Elemen level
data class IA01ElemenSubmission(
    val elemen_id: Int,
    val kuk: List<com.example.mylsp.data.api.assesment.IA01KUKSubmission>
)

// KUK level
data class IA01KUKSubmission(
    val kuk_id: Int,
    val skkni: String, // "ya" atau "tidak"
    val teks_penilaian: String
)

data class PostApproveIa01Response(
    val success: Boolean,
    val message: String
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
