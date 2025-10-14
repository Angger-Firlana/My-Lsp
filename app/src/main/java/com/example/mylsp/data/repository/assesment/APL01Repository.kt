package com.example.mylsp.data.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.data.model.api.Apl01
import com.example.mylsp.data.remote.api.APIClient

class APL01Repository(private val context: Context) {

    private val api = APIClient.getClient(context)

    suspend fun getFormApl01Status(id:Int): Result<Apl01> {
        return try {
            val response = api.getFormApl01StatusByUser(id)

            if(response.isSuccessful) {
                val body = response.body()
                Log.d("APL01Repository", "Raw response: ${response.body()}")

                if(body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body APL01 is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFormApl01StatusByAsesi(id:Int): Result<Apl01> {
        return try {
            val response = api.getFormApl01StatusByUser(id)

            if(response.isSuccessful) {
                val body = response.body()
                Log.d("APL01Repository", "Raw response: ${response.body()}")

                if(body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body APL01 is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFormApl01StatusByUser(id:Int): Result<Apl01> {
        return try {
            val response = api.getFormApl01StatusByUser(id)

            if(response.isSuccessful) {
                val body = response.body()
                Log.d("APL01Repository", "Raw response: ${response.body()}")

                if(body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body APL01 is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
