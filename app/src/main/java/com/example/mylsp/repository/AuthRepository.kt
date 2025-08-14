package com.example.mylsp.repository

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.api.APIService
import com.example.mylsp.model.api.LoginRequest
import com.example.mylsp.model.api.LoginResponse
import com.example.mylsp.model.api.RegisterRequest
import com.example.mylsp.model.api.RegisterResponse

class AuthRepository(context: Context) {
    private val api = APIClient.getClient(context)
    suspend fun login (loginRequest: LoginRequest):Result<LoginResponse>{
        return try {
            val response = api.login(loginRequest)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(Exception("."))
                }
            }else{
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorBody))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun loginAndFetchUser(){

    }

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = api.register(registerRequest)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body kosong"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}