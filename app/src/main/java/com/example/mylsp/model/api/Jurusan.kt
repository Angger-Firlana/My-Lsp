package com.example.mylsp.model.api


data class JurusanResponse(
    val message: String,
    val data: List<Jurusan>
)
data class Jurusan(
    val id: Int,
    val kode_jurusan: String,
    val nama_jurusan:String,
    val jenjang:String,
    val deskripsi: String
)