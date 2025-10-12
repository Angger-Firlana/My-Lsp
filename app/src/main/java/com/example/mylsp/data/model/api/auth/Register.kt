package com.example.mylsp.data.model.api.auth

import com.example.mylsp.model.api.User

data class RegisterRequest(
    val email:String,
    val username:String,
    val password:String,
    val jurusan_id:Int
)

data class RegisterResponse(
    val message:String,
    val user: User
)