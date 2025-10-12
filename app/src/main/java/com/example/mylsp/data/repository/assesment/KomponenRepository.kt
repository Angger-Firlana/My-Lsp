package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.assesment.KomponenResponse

class KomponenRepository(context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun getKomponens():Result<com.example.mylsp.data.api.assesment.KomponenResponse> {
        val response = api.getKomponens()
        return try {
            if (response.isSuccessful){
                val body = response.body()
                if (body!= null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("body is null"))
                }
            }else{
                Result.failure(Exception(response.errorBody().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}