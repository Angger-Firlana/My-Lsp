package com.example.mylsp.api

import com.example.mylsp.model.api.Jurusan
import com.example.mylsp.model.api.LoginRequest
import com.example.mylsp.model.api.LoginResponse
import com.example.mylsp.model.api.RegisterRequest
import com.example.mylsp.model.api.RegisterResponse
import com.example.mylsp.model.api.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    @GET("user")
    suspend fun getUser():Response<User>
    @POST("apl_01")
    suspend fun addApl01()
    @GET("jurusan")
    suspend fun getJurusans():Response<List<Jurusan>>
}