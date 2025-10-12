package com.example.mylsp.data.api.assesment

import com.example.mylsp.data.api.asesi.AssesmentAsesi

data class Ak02Request(
    val assesment_asesi_id: Int,
    val ttd_asesi: String?, // "belum" | "sudah" | null
    val ttd_asesor: String?,
    val rekomendasi_hasil: String, // "kompeten" | "tidak_kompeten"
    val tindak_lanjut: String?,
    val komentar_asesor: String?,// "belum" | "sudah" | null
    val units: List<Ak02Unit>
)

data class Ak02Unit(
    val unit_id: Int,
    val bukti_yang_relevan: List<Ak02Bukti>
)

data class Ak02Bukti(
    val bukti_description: String
)
data class Ak02Response(
    val success: Boolean,
    val message: String,
    val submission_id: Int? = null,
    val error: String? = null
)

data class Ak02GetResponse(
    val success: Boolean,
    val message: String,
    val data: List<AK02GetSubmission>?
)

data class AK02GetSubmission(
    val id: Int,
    val assesment_asesi_id: Int,
    val ttd_asesi: String?,
    val ttd_asesor: String?,

    val rekomendasi_hasil: String,
    val tindak_lanjut: String?,
    val assesmentAsesi: AssesmentAsesi?,
    val details: List<Ak02Detail>
)

data class Ak02Detail(
    val id: Int,
    val ak02_detail_id: Int,
    val unit_id: Int,
    val unit: Unit,
    val bukti: List<AK02GetBukti>
)

data class AK02GetBukti(
    val id: Int,
    val ak02_detail_id: Int,
    val bukti_description: String
)

data class Unit(
    val id: Int,
    val schema_id: Int,
    val unit_ke: Int,
    val kode_unit: String?,
    val judul_unit: String?
)
