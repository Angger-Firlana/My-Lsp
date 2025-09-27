package com.example.mylsp.model.api.assesment

data class KomponenResponse(
    val success: Boolean,
    val data: List<KomponenData>
)

data class KomponenData(
    val id:Int,
    val komponen: String
)