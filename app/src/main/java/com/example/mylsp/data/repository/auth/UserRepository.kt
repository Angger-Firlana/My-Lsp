package com.example.mylsp.data.repository.auth

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.model.api.User

class UserRepository(context:Context) {
    private val api = APIClient.getClient(context)


    suspend fun getUserByToken(): Result<User> {
        return try {
            val response = api.getUser()
            if(response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("response kosong"))
                }
            }else{
                val error = response.errorBody()?.string()?: "error"
                Result.failure(Exception(error))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}