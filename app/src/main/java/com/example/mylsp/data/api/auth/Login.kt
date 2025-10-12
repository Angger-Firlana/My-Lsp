package com.example.mylsp.data.api.auth

data class LoginRequest(val input:String, val password:String)

data class LoginResponse(
    val message:String,
    val token:String,
    val user: com.example.mylsp.data.api.auth.UserLogin
)

data class UserLogin(
    val id: Int,
    val username: String,
    val jurusan_id: Int,
    val role: String
)