package com.example.mylsp.model.api

data class Asesi(
    val id: Int,
    val user_id: Int?,
    val nama_lengkap: String?,
    val nik: String?,
    val tgl_lahir: String?,
    val tempat_lahir: String?,
    val jenis_kelamin:String?,
    val kebangsaan:String?,
    val alamat_rumah:String?,
    val kode_pos:String?,
    val no_telepon_rumah:String?,
    val no_telepon: String?,
    val email:String?,
    val kualifikasi_pendidikan:String?,
    val nama_institusi:String?,
    val jabatan:String?,
    val alamat_kantor: String?,
    val kode_pos_kantor: String?,
    val fax_kantor: String?,
    val email_kantor: String?,
    val created_at: String?,
    val status:String
)