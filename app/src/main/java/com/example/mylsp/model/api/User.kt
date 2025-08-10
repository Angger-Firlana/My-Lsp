package com.example.mylsp.model.api

data class User(
    val user : UserDetail,
    val message:String?
)

data class UserDetail(
    val id:Int,
    val username:String,
    val email:String,
    val role:String
)
