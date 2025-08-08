package com.example.mylsp.model.api

data class LoginRequest(val input:String, val password:String)
data class LoginResponse(
    val message:String,
    val token:String,
    val code:Int
)