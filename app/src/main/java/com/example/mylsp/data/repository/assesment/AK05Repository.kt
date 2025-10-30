package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.data.api.assesment.Ak05SubmissionResponse
import com.example.mylsp.data.api.assesment.GetAk05Response
import retrofit2.HttpException
import java.io.IOException

class Ak05Repository(private val context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun sendSubmission(request: Ak05SubmissionRequest): Result<Ak05SubmissionResponse> {
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

    suspend fun getSubmission(id: Int): Result<GetAk05Response> {
        return try {
            val response = api.getAk05(id = id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val message = "HTTP ${response.code()} - ${response.message()}${if (!errorBody.isNullOrEmpty()) ": $errorBody" else ""}"
                Result.failure(Exception(message))
            }

        } catch (e: IOException) {
            // Network issues, e.g., no internet, timeout
            Result.failure(Exception("Network error: ${e.localizedMessage ?: "unknown"}", e))
        } catch (e: HttpException) {
            // Unexpected non-2xx responses not caught by isSuccessful
            Result.failure(Exception("HTTP exception: ${e.code()} ${e.message()}", e))
        
        } catch (e: Exception) {
            // Fallback for any other kind of exception
            Result.failure(Exception("Unexpected error: ${e.localizedMessage}", e))
        }
    }

}
