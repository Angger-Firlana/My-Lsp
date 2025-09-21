package com.example.mylsp.model.api.auth

data class LoginRequest(val input:String, val password:String)

data class LoginResponse(
    val message:String,
    val token:String,
    val user: UserLogin
)

data class UserLogin(
    val id: Int,
    val username: String,
    val jurusan_id: Int,
    val role: String
)