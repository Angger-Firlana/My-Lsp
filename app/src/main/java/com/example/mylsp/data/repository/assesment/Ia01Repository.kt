package com.example.mylsp.data.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.assesment.IA01GetResponse
import com.example.mylsp.data.api.assesment.IA01Request
import com.example.mylsp.data.api.assesment.IA01Response
import com.example.mylsp.data.api.assesment.PostApproveIa01Response

class Ia01Repository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun postIa01(iA01Request: com.example.mylsp.data.api.assesment.IA01Request): Result<com.example.mylsp.data.api.assesment.IA01Response> {
        return try {
            val response = api.postSubmissionIa01(submissionRequest = iA01Request)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                // Parse error response
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getIA01ByAsesi(asesiId: Int): Result<com.example.mylsp.data.api.assesment.IA01GetResponse> {
        return try {
            val response = api.getIA01ByAsesi(id = asesiId)
            val responseBodyString = response.body()
            Log.e("errorIA01", (responseBodyString ?: "body kosong").toString())

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body IA01 is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postApproveAsesi(id:Int):Result<PostApproveIa01Response>{
        return try{
            val response = api.postApproveIa01ByAsesi(id = id)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown Error"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}