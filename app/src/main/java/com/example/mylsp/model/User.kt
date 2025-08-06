package com.example.mylsp.model

data class User(
    val id_user : Int,
    val username: String,
    val password_hash: String,
    val email: String,
    val role: String,
    val last_login: String,
    val isActive: Boolean,
    val reset_token: String,
    val reset_token_expires: String,
    val created_at: String,
    val updated_at: String
)
