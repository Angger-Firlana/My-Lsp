package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.api.asesi.AssesmentAsesiResponse
import com.example.mylsp.data.api.asesi.DeleteAssesmentAsesiResponse
import com.example.mylsp.data.api.asesi.PatchStatusReq
import com.example.mylsp.data.api.asesi.PatchStatusResponse
import com.example.mylsp.data.api.asesi.PostAssesmentAsesiReq
import com.example.mylsp.data.api.asesi.PostAssesmentAsesiResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import java.io.IOException

class AssesmentAsesiRepository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun postAssesmentAsesi(request: PostAssesmentAsesiReq): Result<PostAssesmentAsesiResponse> {
        return try {
            val response = api.postAssesmentAsesi(request = request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                // Ambil pesan error detail dari response body kalau ada
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (!errorBody.isNullOrEmpty()) {
                    "Server error: $errorBody"
                } else {
                    "HTTP ${response.code()} ${response.message()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            // error koneksi, misalnya timeout atau no internet
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        } catch (e: HttpException) {
            // error dari Retrofit kalau status code gak 2xx
            Result.failure(Exception("HTTP exception: ${e.localizedMessage}"))
        } catch (e: Exception) {
            // error tak terduga lainnya
            Result.failure(Exception("Unexpected error: ${e.localizedMessage}"))
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

    suspend fun deleteAssesmentAsesi(id:Int):Result<DeleteAssesmentAsesiResponse>{
        return try {
            val response = api.deleteAssesmentAsesi(id = id)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
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