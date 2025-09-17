package com.example.mylsp.model.api

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class Asesi(
    val id: Int,
    val user_id: Int?,
    val nama_lengkap: String?,
    val no_ktp: String?,
    val tgl_lahir: String?,
    val tempat_lahir: String?,
    val jenis_kelamin:String?,
    val kebangsaan:String?,
    val alamat_rumah:String?,
    val kode_pos:String?,
    val no_telepon_rumah:String?,
    val no_telepon_kantor:String?,
    val no_telepon: String?,
    val email:String?,
    val kualifikasi_pendidikan:String?,
    val nama_institusi:String?,
    val jabatan:String?,
    val alamat_kantor: String?,
    val kode_pos_kantor: String?,
    val fax_kantor: String?,
    val email_kantor: String?,
    val status:String,
    val user: UserDetail
)

data class AsesiRequest(
    val nama_lengkap: String?,
    val nik: String?,
    val tgl_lahir: String?,
    val tempat_lahir: String?,
    val jenis_kelamin:String?,
    val kebangsaan:String?,
    val alamat_rumah:String?,
    val kode_pos:String?,
    val no_telepon_rumah:String?,
    val no_telepon_kantor:String?,
    val no_telepon: String?,
    val email:String?,
    val kualifikasi_pendidikan:String?,
    val nama_institusi:String?,
    val jabatan:String?,
    val alamat_kantor: String?,
    val kode_pos_kantor: String?,
    val fax_kantor: String?,
    val email_kantor: String?,
    val status:String,
    val attachments: List<AttachmentRequest>
)

data class AttachmentRequest(
    val file: MultipartBody.Part,
    val description: RequestBody
)


data class CreateAsesiResponse(
    val message: String
)