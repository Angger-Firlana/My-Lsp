package com.example.mylsp.data.api.assesment

data class KomponenResponse(
    val success: Boolean,
    val data: List<com.example.mylsp.data.api.assesment.KomponenData>
)

data class KomponenData(
    val id:Int,
    val komponen: String
)