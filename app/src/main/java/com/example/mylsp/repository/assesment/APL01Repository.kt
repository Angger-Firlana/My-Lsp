package com.example.mylsp.repository.assesment

import android.content.Context
import android.util.Log
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.Apl01

class APL01Repository(private val context: Context) {

    private val api = APIClient.getClient(context)

    suspend fun getFormApl01Status(id:Int): Result<Apl01> {
        return try {
            val response = api.getFormApl01Status(id)

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
