package com.example.mylsp.data.repository.assesment

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.saveable.Saver
import com.example.mylsp.data.api.assesment.GetAnswerResponse
import com.example.mylsp.data.api.assesment.PostAnswerRequest
import com.example.mylsp.data.api.assesment.PostAnswerResponse
import com.example.mylsp.data.api.assesment.QuestionResponse
import com.example.mylsp.data.model.api.AsesiRequest
import com.example.mylsp.data.remote.api.APIClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class QuestionRepository(context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun getQuestionBySkema(id:Int):Result<QuestionResponse>{
        val response = api.getQuestionsBySkema(id)

        return try{
            if(response.isSuccessful){
                val body = response.body()
                if (body == null){
                    Result.failure(Exception("body is null"))
                }else{
                    Result.success(body)
                }
            }else{
                Result.failure(Exception(response.errorBody().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }

    }
    suspend fun downloadQuestion(id:Int, savePath:String):Result<File>{
        return try {
            val response = api.downloadQuestion(id)

            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    val file = File(savePath)
                    body.byteStream().use { input ->
                        FileOutputStream(file).use { output ->
                            val buffer = ByteArray(8 * 1024)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                        }
                    }
                    Result.success(file)
                }else{
                    Result.failure(Exception("body is null"))
                }
            }else{
                Result.failure(Exception(response.errorBody()?.string() ?: "Download failed"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun downloadQuestionToUri(id: Int, context: Context, uri: Uri): Result<Unit> {
        return try {
            val response = api.downloadQuestion(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    context.contentResolver.openOutputStream(uri)?.use { output ->
                        body.byteStream().use { input ->
                            val buffer = ByteArray(8 * 1024)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                        }
                    }
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Download failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun downloadAnswerToUri(id: Int, context: Context, uri: Uri): Result<Unit> {
        return try {
            val response = api.downloadAnswer(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    context.contentResolver.openOutputStream(uri)?.use { output ->
                        body.byteStream().use { input ->
                            val buffer = ByteArray(8 * 1024)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                        }
                    }
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Download failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadAnswer(postAnswerRequest: PostAnswerRequest): Result<PostAnswerResponse> {
        return try {
            val (textParts, filePart) = postAnswerRequest.toMultipart()

            val questionId = textParts["question_id"]
                ?: throw IllegalArgumentException("question_id tidak boleh null")
            val assesmentAsesiId = textParts["assesment_asesi_id"]
                ?: throw IllegalArgumentException("assesment_asesi_id tidak boleh null")

            val response = api.postAnswerQuestion(
                question_id = questionId,
                assesment_asesi_id = assesmentAsesiId,
                files = filePart
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Upload failed"
                Log.e("QuestionRepository", "❌ uploadAnswer error: $errorMsg")
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("QuestionRepository", "❌ uploadAnswer error: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getAnswer(): Result<GetAnswerResponse> {
        return try {
            val response = api.getAllAnswer()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("❌ Response body is null"))
                }
            } else {
                // Ambil isi pesan error biar kebaca
                val errorMessage = response.errorBody()?.string()
                Result.failure(
                    Exception("⚠️ Request failed [${response.code()}]: ${errorMessage ?: "Unknown error"}")
                )
            }
        } catch (e: Exception) {
            // Tambahin stacktrace dan message lengkap
            Log.e("GetAnswer", "🔥 Exception: ${e.message}", e)
            Result.failure(Exception("🚨 Exception occurred: ${e.message ?: e.toString()}"))
        }
    }



}

fun PostAnswerRequest.toMultipart(): Pair<Map<String, @JvmSuppressWildcards RequestBody>, MultipartBody.Part> {
    val textParts = mutableMapOf<String, RequestBody>()
    val TAG = "PostAnswerRequest.toMultipart"

    fun addText(name: String, value: String?) {
        if (!value.isNullOrBlank()) {
            textParts[name] = value.toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(TAG, "✅ Added text part: $name = $value")
        } else {
            Log.w(TAG, "⚠️ Skipped null/blank field: $name")
        }
    }

    fun addInt(name: String, value: Int?) {
        if (value != null) {
            textParts[name] = value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(TAG, "✅ Added int part: $name = $value")
        } else {
            Log.w(TAG, "⚠️ Skipped null Int field: $name")
        }
    }

    // Tambahkan field sesuai backend
    addInt("question_id", question_id)
    addInt("assesment_asesi_id", assesment_asesi_id)

    // Pastikan variabel file ada di PostAnswerRequest
    val filePart = files?.let { file ->
        val requestFile = file
            .asRequestBody("application/vnd.openxmlformats-officedocument.wordprocessingml.document".toMediaTypeOrNull())

        MultipartBody.Part.createFormData(
            "files",        // nama field di backend
            file.name,      // nama file aslinya
            requestFile     // isi file + tipe MIME
        )
    } ?: throw IllegalArgumentException("File tidak boleh null")


    return textParts to filePart
}

