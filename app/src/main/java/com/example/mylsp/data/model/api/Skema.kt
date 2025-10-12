package com.example.mylsp.data.model.api

data class Skemas(
    val success: Boolean,
    val data: List<com.example.mylsp.data.model.api.SkemaDetail>
)

data class SkemaDetail(
    val id: Int,
    val judul_skema: String,
    val nomor_skema: String,
    val jurusan: com.example.mylsp.model.api.Jurusan,
    val total_units: Int,
    val total_elements: Int,
    val total_kuk: Int,
    val tanggal_mulai: String?,   // nullable karena null di JSON
    val tanggal_selesai: String?, // nullable
    val created_at: String
)
