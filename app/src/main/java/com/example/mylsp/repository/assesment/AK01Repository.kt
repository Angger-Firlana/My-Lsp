package com.example.mylsp.repository.assesment

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.assesment.AK01Submission
import com.example.mylsp.model.api.assesment.AK01SubmissionResponse
import com.example.mylsp.model.api.assesment.GetAK01Response
import com.example.mylsp.model.api.assesment.PostApproveRequest
import com.example.mylsp.model.api.assesment.PostApproveResponse

class AK01Repository(private val context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun sendSubmission(aK01SubmissionRequest: AK01Submission):Result<AK01SubmissionResponse>{
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

    suspend fun getSubmission(id:Int):Result<GetAK01Response>{
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

    suspend fun postApproveAK01(id:Int):Result<PostApproveResponse>{
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