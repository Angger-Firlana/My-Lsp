package com.example.mylsp.repository.assesment

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.KomponenResponse

class KomponenRepository(context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun getKomponens():Result<KomponenResponse> {
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