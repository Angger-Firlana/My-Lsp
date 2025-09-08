package com.example.mylsp.repository

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.AK01SubmissionRequest
import com.example.mylsp.model.api.AK01SubmissionResponse
import okhttp3.Response

class AK01Repository(private val context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun sendSubmission(aK01SubmissionRequest: AK01SubmissionRequest):Result<AK01SubmissionResponse>{
        return try {
            val response = api.sendSubmissionAk01(
                assessmentAsesiId = aK01SubmissionRequest.assesmentAsesiId,
                attachments = aK01SubmissionRequest.attachments
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
}