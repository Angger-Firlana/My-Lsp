package com.example.mylsp.repository.assesment

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.PostAK03Request
import com.example.mylsp.model.api.assesment.PostAK03Response

class AK03Repository(context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun postAK03(aK03Request: PostAK03Request): Result<PostAK03Response> {
        return try {
            val response = api.postSubmissionAk03(bodyRequestBody = aK03Request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                // Ambil errorBody biar lebih jelas
                val errorMessage = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Unknown error while reading errorBody"
                }

                Result.failure(Exception("Error ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Request failed: ${e.localizedMessage}", e))
        }
    }

}