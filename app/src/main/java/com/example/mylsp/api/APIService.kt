package com.example.mylsp.api

import com.example.mylsp.model.User
import retrofit2.http.GET

interface APIService {
    @GET("users")
    suspend fun getUsers(): List<User>

}