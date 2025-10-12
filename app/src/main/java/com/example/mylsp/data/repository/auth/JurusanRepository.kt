package com.example.mylsp.data.repository.auth

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.model.api.JurusanResponse

class JurusanRepository(context: Context) {
    private val api = APIClient.getClient(context)
    suspend fun getJurusans():Result<JurusanResponse>{
        return try {
            val response = api.getJurusans()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}