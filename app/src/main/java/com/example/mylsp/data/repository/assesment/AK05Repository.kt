package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.model.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.data.model.api.assesment.Ak05SubmissionResponse
import com.example.mylsp.data.model.api.assesment.GetAk05Response

class Ak05Repository(private val context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun sendSubmission(request: com.example.mylsp.data.model.api.assesment.Ak05SubmissionRequest): Result<com.example.mylsp.data.model.api.assesment.Ak05SubmissionResponse> {
        return try {
            val response = api.postAk05(bodyRequestBody = request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception(errorBody ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubmission(id: Int): Result<com.example.mylsp.data.model.api.assesment.GetAk05Response> {
        return try {
            val response = api.getAk05(id = id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
