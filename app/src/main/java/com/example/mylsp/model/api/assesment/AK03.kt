package com.example.mylsp.model.api.assesment

import android.os.Message

data class PostAK03Request(
    val assesment_asesi_id:Int,
    val catatan_tambahan:String,
    val komponen: List<KomponenPostReq>
)

data class KomponenPostReq(
    val komponen_id: Int,
    var hasil: String,
    var catatan_asesi: String
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
