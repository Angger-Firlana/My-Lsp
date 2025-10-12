package com.example.mylsp.data.repository.auth

import android.content.Context
import android.util.Log
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.model.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class AsesiRepository(context: Context) {
    private val api = APIClient.getClient(context)
    private val TAG = "AsesiRepository"

    suspend fun createDataAsesi(asesiRequest: AsesiRequest): Result<CreateAsesiResponse> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "==[CREATE DATA ASESIS]==")
                Log.d(TAG, "Input Data: $asesiRequest")

                val (textParts, fileParts) = asesiRequest.toMultipart()
                Log.d(TAG, "Prepared text parts (${textParts.size}): ${textParts.keys}")
                Log.d(TAG, "Prepared file parts: ${fileParts.size}")

                val response = api.createApl01(
                    "application/json",
                    textParts["nama_lengkap"],
                    textParts["nik"],
                    textParts["tgl_lahir"],
                    textParts["tempat_lahir"],
                    textParts["jenis_kelamin"],
                    textParts["kebangsaan"],
                    textParts["alamat_rumah"],
                    textParts["kode_pos"],
                    textParts["no_telepon_rumah"],
                    textParts["no_telepon_kantor"],
                    textParts["no_telepon"],
                    textParts["email"],
                    textParts["kualifikasi_pendidikan"],
                    textParts["nama_institusi"],
                    textParts["jabatan"],
                    textParts["alamat_kantor"],
                    textParts["kode_pos_kantor"],
                    textParts["fax_kantor"],
                    textParts["email_kantor"],
                    textParts["status"],
                    textParts["tujuan_asesmen"],
                    textParts["schema_id"],
                    fileParts
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.i(TAG, "✅ Success: $it")
                        Result.success(it)
                    } ?: Result.failure(Exception("Response kosong"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "❌ API Error [${response.code()}]: ${errorBody ?: response.message()}")
                    Result.failure(Exception("HTTP ${response.code()} - ${errorBody ?: "Unknown error"}"))
                }

            } catch (e: IOException) {
                Log.e(TAG, "🌐 Network error: ${e.message}", e)
                Result.failure(Exception("Tidak bisa terhubung ke server, cek koneksi internet."))
            } catch (e: HttpException) {
                Log.e(TAG, "⚠️ HTTP exception: ${e.code()} - ${e.message()}", e)
                Result.failure(Exception("Server error (${e.code()}): ${e.message()}"))
            } catch (e: Exception) {
                Log.e(TAG, "💥 Unknown exception in createDataAsesi", e)
                Result.failure(Exception("Terjadi kesalahan: ${e.localizedMessage ?: e.message}"))
            }
        }

    suspend fun updateDataAsesi(id: Int, asesiRequest: AsesiRequest): Result<CreateAsesiResponse> =
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "==[UPDATE DATA ASESIS]== id=$id")
                Log.d(TAG, "Input Data: $asesiRequest")

                val (textParts, fileParts) = asesiRequest.toMultipart()
                Log.d(TAG, "Prepared text parts (${textParts.size}): ${textParts.keys}")
                Log.d(TAG, "Prepared file parts: ${fileParts.size}")

                val response = api.updateApl01(
                    "application/json",
                    id,
                    textParts["nama_lengkap"],
                    textParts["nik"],
                    textParts["tgl_lahir"],
                    textParts["tempat_lahir"],
                    textParts["jenis_kelamin"],
                    textParts["kebangsaan"],
                    textParts["alamat_rumah"],
                    textParts["kode_pos"],
                    textParts["no_telepon_rumah"],
                    textParts["no_telepon_kantor"],
                    textParts["no_telepon"],
                    textParts["email"],
                    textParts["kualifikasi_pendidikan"],
                    textParts["nama_institusi"],
                    textParts["jabatan"],
                    textParts["alamat_kantor"],
                    textParts["kode_pos_kantor"],
                    textParts["fax_kantor"],
                    textParts["email_kantor"],
                    textParts["status"],
                    textParts["tujuan_asesmen"],
                    textParts["schema_id"],
                    fileParts
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.i(TAG, "✅ Update Success: $it")
                        Result.success(it)
                    } ?: Result.failure(Exception("Response kosong"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "❌ API Error [${response.code()}]: ${errorBody ?: response.message()}")
                    Result.failure(Exception("HTTP ${response.code()} - ${errorBody ?: "Unknown error"}"))
                }

            } catch (e: IOException) {
                Log.e(TAG, "🌐 Network error: ${e.message}", e)
                Result.failure(Exception("Tidak bisa terhubung ke server, cek koneksi internet."))
            } catch (e: HttpException) {
                Log.e(TAG, "⚠️ HTTP exception: ${e.code()} - ${e.message()}", e)
                Result.failure(Exception("Server error (${e.code()}): ${e.message()}"))
            } catch (e: Exception) {
                Log.e(TAG, "💥 Unknown exception in updateDataAsesi", e)
                Result.failure(Exception("Terjadi kesalahan: ${e.localizedMessage ?: e.message}"))
            }
        }

    suspend fun getDataAsesis(): Result<AsesiResponse> = try {
        val response = api.getAssesis()
        if (response.isSuccessful) {
            response.body()?.let { Result.success(it) }
                ?: Result.failure(Exception("Response body kosong"))
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e(TAG, "❌ getDataAsesis Error: ${response.code()} - ${errorBody ?: "Unknown"}")
            Result.failure(Exception("HTTP ${response.code()} - ${errorBody ?: "Unknown"}"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "💥 Exception in getDataAsesis", e)
        Result.failure(e)
    }

    suspend fun getApl01ByUser(id: Int): Result<Apl01> = try {
        val response = api.getApl01ByUser(id)
        if (response.isSuccessful) {
            response.body()?.let { Result.success(it) }
                ?: Result.failure(Exception("Response body kosong"))
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e(TAG, "❌ getApl01ByUser Error: ${response.code()} - ${errorBody ?: "Unknown"}")
            Result.failure(Exception("HTTP ${response.code()} - ${errorBody ?: "Unknown"}"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "💥 Exception in getApl01ByUser", e)
        Result.failure(e)
    }
}

fun AsesiRequest.toMultipart(): Pair<Map<String, RequestBody>, List<MultipartBody.Part>> {
    val textParts = mutableMapOf<String, RequestBody>()
    val TAG = "AsesiRequest.toMultipart"

    fun addText(name: String, value: String?) {
        if (!value.isNullOrBlank()) {
            textParts[name] = value.toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(TAG, "Added text part: $name = $value")
        } else {
            Log.w(TAG, "Skipped null/blank field: $name")
        }
    }

    fun addInt(name: String, value: Int?) {
        if (value != null) {
            textParts[name] = value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(TAG, "Added int part: $name = $value")
        } else {
            Log.w(TAG, "Skipped null Int field: $name")
        }
    }

    // Semua field aman null
    addText("nama_lengkap", nama_lengkap)
    addText("nik", nik)
    addText("tgl_lahir", tgl_lahir)
    addText("tempat_lahir", tempat_lahir)
    addText("jenis_kelamin", jenis_kelamin)
    addText("kebangsaan", kebangsaan)
    addText("alamat_rumah", alamat_rumah)
    addText("kode_pos", kode_pos)
    addText("no_telepon_rumah", no_telepon_rumah)
    addText("no_telepon_kantor", no_telepon_kantor)
    addText("no_telepon", no_telepon)
    addText("email", email)
    addText("kualifikasi_pendidikan", kualifikasi_pendidikan)
    addText("nama_institusi", nama_institusi)
    addText("jabatan", jabatan)
    addText("alamat_kantor", alamat_kantor)
    addText("kode_pos_kantor", kode_pos_kantor)
    addText("fax_kantor", fax_kantor)
    addText("email_kantor", email_kantor)
    addText("status", status)
    addText("tujuan_asesmen", tujuan_asesmen)
    addInt("schema_id", schema_id)

    val multipartList = mutableListOf<MultipartBody.Part>()

    attachments?.forEachIndexed { index, part ->
        try {
            multipartList.add(
                MultipartBody.Part.createFormData(
                    "attachments[$index][file]",
                    "file_${index}",
                    part.file.body
                )
            )
            multipartList.add(
                MultipartBody.Part.createFormData(
                    "attachments[$index][description]",
                    part.description.toString()
                )
            )
            multipartList.add(
                MultipartBody.Part.createFormData(
                    "attachments[$index][nama_dokumen]",
                    part.description.toString()
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error adding attachment $index: ${e.message}")
        }
    }

    Log.d(TAG, "Final textParts count: ${textParts.size}")
    Log.d(TAG, "Final multipartList count: ${multipartList.size}")

    return textParts to multipartList
}
