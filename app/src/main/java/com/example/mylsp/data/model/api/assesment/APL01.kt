package com.example.mylsp.data.model.api.assesment

data class FormApl01Response(
    val message: String,
    val data: com.example.mylsp.data.model.api.assesment.FormApl01Data
)

data class FormApl01Data(
    val id: Int,
    val status: String,
    val created_at: String? = null
)