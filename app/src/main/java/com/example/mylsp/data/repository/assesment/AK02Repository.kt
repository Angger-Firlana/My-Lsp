package com.example.mylsp.data.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.assesment.Ak02Request
import com.example.mylsp.data.api.assesment.Ak02Response
import com.example.mylsp.data.api.assesment.Ak02GetResponse
import retrofit2.HttpException
import java.io.IOException

class AK02Repository(private val context: Context) {

    private val api = APIClient.getClient(context)

    // POST Form AK02
    suspend fun postAK02(request: Ak02Request): Result<Ak02Response> {
        return try {
            val response = api.postSubmissionAk02(bodyRequestBody = request)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("AK02Repository", "Raw response (POST): $body")

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body AK02 is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // GET Ak02 by Assesi
    suspend fun getAk02ByAssesi(assesiId: Int): Result<Ak02GetResponse> {
        return try {
            val response = api.getAK02ByAsesi(id = assesiId)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("AK02Repository", "Raw response (GET): $body")

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body Ak02Get is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun patchTtdAsesi(submissionId: Int): Result<Ak02Response> {
        return try {
            val response = api.updateStatusAsesi(id = submissionId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Log.e("AK02Repository", "Response body is null")
                    Result.failure(Exception("Server returned empty response body"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown server error"
                Log.e("AK02Repository", "HTTP ${response.code()}: $errorMessage")
                Result.failure(Exception("Request failed with code ${response.code()}: $errorMessage"))
            }

        } catch (e: HttpException) {
            Log.e("AK02Repository", "HTTP Exception: ${e.code()} - ${e.message()}")
            Result.failure(Exception("HTTP error ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            Log.e("AK02Repository", "Network error: ${e.message}")
            Result.failure(Exception("Network error, please check your internet connection"))
        } catch (e: Exception) {
            Log.e("AK02Repository", "Unexpected error: ${e.localizedMessage}")
            Result.failure(Exception("Unexpected error: ${e.localizedMessage}"))
        }
    }

}
