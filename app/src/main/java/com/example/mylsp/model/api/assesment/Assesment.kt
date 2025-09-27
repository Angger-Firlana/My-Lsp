package com.example.mylsp.model.api.assesment

data class AssessmentResponse(
    val success: Boolean,
    val message: String,
    val data: List<Assessment>
)

data class GetAssesmentResponse(
    val success: Boolean,
    val message: String,
    val data: Assessment
)

data class Assessment(
    val id: Int,
    val skema_id: Int,
    val admin_id: Int,
    val assesor_id: Int,
    val tanggal_assesment: String,
    val status: String,
    val tuk: String,
    val created_at: String?,
    val updated_at: String?,
    val schema: Schema,
    val admin: Admin,
    val assesor: Assessor
)

data class Schema(
    val id: Int,
    val jurusan_id: Int,
    val judul_skema: String,
    val nomor_skema: String,
    val tanggal_mulai: String?,
    val tanggal_selesai: String?,
    val created_at: String?,
    val updated_at: String?
)

data class Admin(
    val id_admin: Int,
    val user_id: Int,
    val nama_lengkap: String,
    val email: String,
    val no_hp: String,
    val role: String,
    val status: String,
    val created_at: String?,
    val updated_at: String?
)

data class Assessor(
    val id: Int,
    val user_id: Int,
    val nama_lengkap: String,
    val no_registrasi: String,
    val email: String,
    val jenis_kelamin: String,
    val no_telepon: String,
    val kompetensi: String,
    val created_at: String?,
    val updated_at: String?
)
