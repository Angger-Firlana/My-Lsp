package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.model.api.assesment.AK01Submission
import com.example.mylsp.data.model.api.assesment.AK01SubmissionResponse
import com.example.mylsp.data.model.api.assesment.GetAK01Response
import com.example.mylsp.data.model.api.assesment.PostApproveRequest
import com.example.mylsp.data.model.api.assesment.PostApproveResponse

class AK01Repository(private val context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun sendSubmission(aK01SubmissionRequest: com.example.mylsp.data.model.api.assesment.AK01Submission):Result<com.example.mylsp.data.model.api.assesment.AK01SubmissionResponse>{
        return try {
            val response = api.sendSubmissionAk01(
                bodyRequestBody = aK01SubmissionRequest
            )

            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                val errorBody = response.errorBody()
                Result.failure(Exception(errorBody.toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun getSubmission(id:Int):Result<com.example.mylsp.data.model.api.assesment.GetAK01Response>{
        return try {
            val response = api.getAk01ByAsesi(id = id)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else {
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception("Ngebug"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun postApproveAK01(id:Int):Result<_root_ide_package_.com.example.mylsp.data.model.api.assesment.PostApproveResponse>{
        return try {
            val response = api.postApproveAk01(id = id)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("Body is null"))
                }
            }else{
                val errorBody = response.errorBody()
                Result.failure(Exception(errorBody.toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}