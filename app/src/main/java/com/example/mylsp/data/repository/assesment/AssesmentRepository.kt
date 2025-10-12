package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.assesment.Assessment
import com.example.mylsp.data.api.assesment.AssessmentResponse
import com.example.mylsp.data.api.assesment.GetAssesmentResponse

class AssesmentRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun getAssesments():Result<com.example.mylsp.data.api.assesment.AssessmentResponse>{
        return try {
            val response = api.getAssesments()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else {
                    Result.failure(Exception("Response Kosong"))
                }
            }else{
                val errorBody = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorBody))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun getAssesmentById(id:Int):Result<com.example.mylsp.data.api.assesment.GetAssesmentResponse>{
        return try {
            val response = api.getAssesmentById(id)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else {
                    Result.failure(Exception("Response Kosong"))
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