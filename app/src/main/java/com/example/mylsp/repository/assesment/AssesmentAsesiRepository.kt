package com.example.mylsp.repository.assesment

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.asesi.AssesmentAsesiResponse
import com.example.mylsp.model.api.asesi.PatchStatusReq
import com.example.mylsp.model.api.asesi.PatchStatusResponse
import com.example.mylsp.model.api.asesi.PostAssesmentAsesiReq
import com.example.mylsp.model.api.asesi.PostAssesmentAsesiResponse

class AssesmentAsesiRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun postAssesmentAsesi(request: PostAssesmentAsesiReq):Result<PostAssesmentAsesiResponse>{
        return try {
            val response = api.postAssesmentAsesi(request = request)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception(response.message()))
            }
        }
        catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun getAssesmentAsesiByAsesi(asesiId:Int):Result<AssesmentAsesiResponse>{
        return try {
            val response = api.getAssesmentAsesiByAsesi(asesiId)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception(errorBody.toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun getAssesmentAsesiByAssesment(assesmentId: Int):Result<AssesmentAsesiResponse>{
        return try {
            val response = api.getAssesmentAsesiByAssesment(assesmentId)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                val errorBody = response.errorBody()
                Result.failure(Exception(errorBody.toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun patchStatus(assesmentAsesiId: Int,request: PatchStatusReq):Result<PatchStatusResponse>{
        return try {
            val response = api.patchStatusAssesmentAsesi(id = assesmentAsesiId, request = request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()
                Result.failure(Exception(errorBody.toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}