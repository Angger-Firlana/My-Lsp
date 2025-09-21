package com.example.mylsp.repository.auth

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.Apl01
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.model.api.AsesiResponse
import com.example.mylsp.model.api.CreateAsesiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AsesiRepository(context: Context) {
    private val api = APIClient.getClient(context)
    private val TAG = "AsesiRepository"

    suspend fun createDataAsesi(asesiRequest: AsesiRequest): Result<CreateAsesiResponse> {
        return try {
            // Debug log input data
            Log.d(TAG, "Input Data - schema_id: ${asesiRequest.schema_id}")
            Log.d(TAG, "Input Data - tujuan_assesment: ${asesiRequest.tujuan_assesment}")

            val (textParts, fileParts) = asesiRequest.toMultipart()

            // Debug: Check if keys exist in textParts
            Log.d(TAG, "TextParts contains schema_id: ${textParts.containsKey("schema_id")}")
            Log.d(TAG, "TextParts contains tujuan_assesment: ${textParts.containsKey("tujuan_assesment")}")
            Log.d(TAG, "All textParts keys: ${textParts.keys}")

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
                textParts["tujuan_assesment"]!!,
                textParts["schema_id"]!!,
                fileParts
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d(TAG, "Success: ${body}")
                    Result.success(body)
                } else {
                    Log.e(TAG, "Response body is null")
                    Result.failure(Exception("Response Kosong"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Log.e(TAG, "API Error: $errorBody")
                Result.failure(Exception(errorBody))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in createDataAsesi", e)
            Result.failure(e)
        }
    }

    suspend fun getDataAsesis(): Result<AsesiResponse> {
        return try {
            val response = api.getAssesis()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response Kosong"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getApl01ByUser(id: Int): Result<Apl01> {
        return try {
            val response = api.getApl01ByUser(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response Body Is Null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun AsesiRequest.toMultipart(): Pair<Map<String, RequestBody>, List<MultipartBody.Part>> {
    val textParts = mutableMapOf<String, RequestBody>()
    val TAG = "AsesiRequest.toMultipart"

    fun String?.asPart(name: String) {
        Log.d(TAG, "Processing String field: $name = '$this'")
        this?.let {
            if (it.isNotBlank()) {
                textParts[name] = it.toRequestBody("text/plain".toMediaTypeOrNull())
                Log.d(TAG, "Added $name to textParts")
            } else {
                Log.w(TAG, "$name is blank, not added to textParts")
            }
        } ?: run {
            Log.w(TAG, "$name is null, not added to textParts")
        }
    }

    fun Int?.asPart(name: String) {
        Log.d(TAG, "Processing Int field: $name = $this")
        this?.let {
            textParts[name] = it.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(TAG, "Added $name to textParts with value: $it")
        } ?: run {
            Log.w(TAG, "$name is null, not added to textParts")
        }
    }

    // Process all fields
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
    tujuan_assesment.asPart("tujuan_assesment")
    schema_id.asPart("schema_id")

    // Debug: Print final textParts
    Log.d(TAG, "Final textParts keys: ${textParts.keys}")
    Log.d(TAG, "Total textParts count: ${textParts.size}")

    val multipartList = mutableListOf<MultipartBody.Part>()

    attachments?.forEachIndexed { index, part ->
        Log.d(TAG, "Processing attachment $index")

        // File
        multipartList.add(
            MultipartBody.Part.createFormData(
                "attachments[$index][file]",
                part.file.body.contentType().toString(),
                part.file.body
            )
        )

        // Description
        multipartList.add(
            MultipartBody.Part.createFormData(
                "attachments[$index][description]",
                null,
                part.description
            )
        )
    }

    Log.d(TAG, "Total multipart attachments: ${multipartList.size}")

    return textParts to multipartList
}