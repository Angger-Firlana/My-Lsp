package com.example.mylsp.repository

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.model.api.CreateAsesiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.RequestBody.Companion.asRequestBody
class AsesiRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun createDataAsesi(asesiRequest:AsesiRequest):Result<CreateAsesiResponse>{
        return try {

            val (textParts, fileParts) = asesiRequest.toMultipart()

            val response = api.createApl01(
                "application/json",
                textParts["nama_lengkap"]!!,
                textParts["nik"]!!,
                textParts["tgl_lahir"]!!,
                textParts["tempat_lahir"]!!,
                textParts["jenis_kelamin"]!!,
                textParts["kebangsaan"]!!,
                textParts["alamat_rumah"]!!,
                textParts["kode_pos"]!!,
                textParts["no_telepon_rumah"]!!,
                textParts["no_telepon_kantor"]!!,
                textParts["no_telepon"]!!,
                textParts["email"]!!,
                textParts["kualifikasi_pendidikan"]!!,
                textParts["nama_institusi"]!!,
                textParts["jabatan"]!!,
                textParts["alamat_kantor"]!!,
                textParts["kode_pos_kantor"]!!,
                textParts["fax_kantor"]!!,
                textParts["email_kantor"]!!,
                textParts["status"]!!,
                fileParts
            )

            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response Kosong"))
                }
            }else{
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }

        }catch (e:Exception){
            Result.failure(e)

        }
    }
}

fun AsesiRequest.toMultipart(): Pair<Map<String, RequestBody>, List<MultipartBody.Part>> {
    val textParts = mutableMapOf<String, RequestBody>()

    fun String?.asPart(name: String) {
        this?.let {
            textParts[name] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }
    }

    nama_lengkap.asPart("nama_lengkap")
    nik.asPart("nik")
    tgl_lahir.asPart("tgl_lahir")
    tempat_lahir.asPart("tempat_lahir")
    jenis_kelamin.asPart("jenis_kelamin")
    kebangsaan.asPart("kebangsaan")
    alamat_rumah.asPart("alamat_rumah")
    kode_pos.asPart("kode_pos")
    no_telepon_rumah.asPart("no_telepon_rumah")
    no_telepon_kantor.asPart("no_telepon_kantor")
    no_telepon.asPart("no_telepon")
    email.asPart("email")
    kualifikasi_pendidikan.asPart("kualifikasi_pendidikan")
    nama_institusi.asPart("nama_institusi")
    jabatan.asPart("jabatan")
    alamat_kantor.asPart("alamat_kantor")
    kode_pos_kantor.asPart("kode_pos_kantor")
    fax_kantor.asPart("fax_kantor")
    email_kantor.asPart("email_kantor")
    status.asPart("status")

    // Ubah attachments jadi MultipartBody.Part list
    val fileParts = attachments.map { part ->
        MultipartBody.Part.createFormData("attachments[]", part.body.contentType().toString(), part.body)
    }

    return textParts to fileParts
}



