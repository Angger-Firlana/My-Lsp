package com.example.mylsp.model.api

import com.google.android.datatransport.cct.StringMerger

data class RegisterRequest(
    val email:String,
    val username:String,
    val password:String,
    val jurusan_id:Int
)

data class RegisterResponse(
    val message:String,
    val token:String,
    val code:Int
)