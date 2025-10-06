package com.example.mylsp.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.Ak02Request
import com.example.mylsp.model.api.assesment.Ak02Response
import com.example.mylsp.model.api.assesment.Ak02GetResponse

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
        return try{
            val response = api.updateStatusAsesi(id = submissionId)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception("Request failed: ${response.errorBody()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}
