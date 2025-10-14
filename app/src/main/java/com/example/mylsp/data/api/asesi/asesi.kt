package com.example.mylsp.data.model.api

import com.example.mylsp.model.api.Jurusan
import com.example.mylsp.model.api.User
import com.example.mylsp.model.api.UserDetail
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class Asesi(
    val id: Int,
    val user_id: Int?,
    val nama_lengkap: String?,
    val no_ktp: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val alamat: String?,
    val kode_pos: String?,
    val no_telepon: String?,
    val email: String?,
    val kualifikasi_pendidikan: String?,
    val nama_institusi: String?,
    val user: User,
    val jurusan: Jurusan
)

data class AsesiResponse(
    val success: Boolean,
    val message: String,
    val data: List<Asesi>
)

data class Apl01(
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
    val user: UserDetail,
    val attachments: List<Attachment>?,
    val sertification_data: SertificationData?
)

data class Attachment(
    val id: Int,
    @SerializedName("form_apl01_id")
    val formApl01Id: Int,
    @SerializedName("nama_dokumen")
    val namaDokumen: String,
    @SerializedName("file_path")
    val filePath: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("view_url")
    val viewUrl: String
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
    val tujuan_asesmen: String,
    val schema_id: Int,
    val attachments: List<AttachmentRequest>
)

data class SertificationData(
    val id: Int,
    val form_apl01_id: Int,
    val schema_id: Int,
    val tujuan_asesmen: String,
    val created_at: String,
    val updated_at: String
)


data class AttachmentRequest(
    val file: MultipartBody.Part,
    val description: RequestBody
)


data class CreateAsesiResponse(
    val message: String
)