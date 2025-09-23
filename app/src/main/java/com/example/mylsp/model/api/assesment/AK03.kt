package com.example.mylsp.model.api.assesment

import android.os.Message

data class PostAK03Request(
    val assesment_asesi_id:Int,
    val catatan_tambahan:String,
    val komponen: List<Komponen>
)

data class Komponen(
    val komponen_id: Int,
    val hasil: String,
    val catatan_hasil: String
)

data class PostAK03Response(
    val success: Boolean,
    val message: String,
    val data : String?
)

data class getAK03Response(
    val success: Boolean,
    val message: String,

)

data class AK03(
    val assesment_asesi_id: Int
)
