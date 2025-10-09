package com.example.mylsp.api

import android.content.Context
import com.example.mylsp.util.user.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://asessment24.site/berdikari/my-lsp-api/public/api/"

    fun getClient(context:Context):APIService{
        val tokenManager = TokenManager(context)

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
            val token = tokenManager.getToken()
            if (!token.isNullOrEmpty()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(builder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}
