package com.example.mylsp.model.api

data class FormApl01Response(
    val message: String,
    val data: FormApl01Data
)

data class FormApl01Data(
    val id: Int,
    val status: String,
    val created_at: String? = null
)