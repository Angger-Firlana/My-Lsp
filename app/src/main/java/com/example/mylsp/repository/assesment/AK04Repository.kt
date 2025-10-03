package com.example.mylsp.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.AK04
import com.example.mylsp.model.api.assesment.GetAK04QuestionResponse
import com.example.mylsp.model.api.assesment.GetAK04Response
import com.example.mylsp.model.api.assesment.PostAK04Response

class Ak04Repository(context: Context) {
    private val api = APIClient.getClient(context)

    // Post AK04
    suspend fun postAk04(request: AK04): Result<PostAK04Response> {
        return try {
            val response = api.postAk04(bodyRequestBody = request)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get AK04 by Asesi
    suspend fun getAk04ByAsesi(asesiId: Int): Result<GetAK04Response> {
        return try {
            val response = api.getAk04ByAsesi(id = asesiId)
            Log.d("Ak04Repository", "Response: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body AK04 is null"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get list pertanyaan AK04
    suspend fun getAk04Questions(): Result<GetAK04QuestionResponse> {
        return try {
            val response = api.getAk04Questions()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body questions is null"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
