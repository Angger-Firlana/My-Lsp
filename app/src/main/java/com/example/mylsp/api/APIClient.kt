package com.example.mylsp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
<<<<<<< HEAD
    private const val BASE_URL = "http://10.0.2.2:3000/"
=======
    private const val BASE_URL = "/api"
>>>>>>> origin/master

    val api:APIService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}