package com.example.mylsp.repository

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.Apl02

class APL02Repository(context:Context){
    val api = APIClient.getClient(context)
    suspend fun getApl02(id:Int):Result<Apl02>{
        return try {
            val response = api.getAPL02(id)
            if (response.isSuccessful){
                val body = response.body()
                if(body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Response Body Is Null"))
                }
            }else{
                val errorBody = response.errorBody()?.string()?: "Unknown error"
                Result.failure(Exception(errorBody))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}