package com.example.mylsp.model.api

data class User(
    val user : userDetail,
    val message:String?
)

data class userDetail(
    val id:Int,
    val username:String,
    val email:String,
    val role:String
)
