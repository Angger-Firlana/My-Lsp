package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.model.api.Skemas

class SkemaRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun getListSkema():Result<com.example.mylsp.data.model.api.Skemas>{
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