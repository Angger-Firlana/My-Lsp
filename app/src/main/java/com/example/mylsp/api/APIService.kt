package com.example.mylsp.api

<<<<<<< HEAD
import com.example.lsp24.models.SkemaSertifikasi
=======
>>>>>>> origin/master
import com.example.mylsp.model.User
import retrofit2.http.GET

interface APIService {
    @GET("users")
    suspend fun getUsers(): List<User>

<<<<<<< HEAD
    @GET("skema_sertifikasi")
    suspend fun getSkemaSertifikasi():  List<SkemaSertifikasi>
=======
>>>>>>> origin/master
}