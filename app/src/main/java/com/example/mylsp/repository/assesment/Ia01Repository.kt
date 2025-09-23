package com.example.mylsp.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.IA01GetResponse
import com.example.mylsp.model.api.assesment.IA01Request
import com.example.mylsp.model.api.assesment.IA01Response

class Ia01Repository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun postIa01(iA01Request: IA01Request):Result<IA01Response>{
        return try {
            val response = api.postSubmissionIa01(submissionRequest = iA01Request)
            val responseBodyString = response.body()
            Log.e("errorIA01", (responseBodyString ?: "body kosong").toString())
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response Body Is Null"))
                }
            }else{
                Result.failure(Exception(response.errorBody().toString()))
            }
        }catch (e:Exception){

            Result.failure(e)
        }
    }

    suspend fun getIA01ByAsesi(asesiId: Int): Result<IA01GetResponse> {
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

}