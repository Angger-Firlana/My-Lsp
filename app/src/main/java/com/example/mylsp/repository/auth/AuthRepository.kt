package com.example.mylsp.repository.auth

import android.content.Context
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.api.auth.LoginRequest
import com.example.mylsp.model.api.auth.LoginResponse
import com.example.mylsp.model.api.auth.RegisterRequest
import com.example.mylsp.model.api.auth.RegisterResponse
import com.google.gson.stream.JsonReader

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

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = api.register("application/json",registerRequest)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body kosong"))
                }
            } else {
                val errorBodyReader = response.errorBody()?.charStream()
                val errorMessage = errorBodyReader?.let {
                    val reader = JsonReader(it)
                    var message = "Unknown error"

                    reader.beginObject()
                    while (reader.hasNext()) {
                        val name = reader.nextName()
                        if (name.equals("message", ignoreCase = true)) {
                            message = reader.nextString()
                        } else {
                            reader.skipValue()
                        }
                    }
                    reader.endObject()
                    reader.close()
                    message
                } ?: "Unknown error"

                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}