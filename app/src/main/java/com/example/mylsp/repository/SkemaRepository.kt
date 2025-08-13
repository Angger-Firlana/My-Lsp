package com.example.mylsp.repository

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.Skemas

class SkemaRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun getListSkema():Result<Skemas>{
        return try {
            val response = api.getListSkema()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response is empty"))
                }
            }else{
                val errorBody = response.errorBody()?.string()?:"Unknown Error"
                Result.failure(Exception(errorBody))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}