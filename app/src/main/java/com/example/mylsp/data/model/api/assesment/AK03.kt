package com.example.mylsp.data.model.api.assesment

data class PostAK03Request(
    val assesment_asesi_id:Int,
    val catatan_tambahan:String,
    val komponen: List<com.example.mylsp.data.model.api.assesment.KomponenPostReq>
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

data class GetAK03Response(
    val success: Boolean,
    val message: String,
    val data: List<com.example.mylsp.data.model.api.assesment.GetAK03Data>
)

data class GetAK03Data(
    val id: Int,
    val assesment_asesi_id: Int,
    val catatan_tambahan: String,
    val details: List<com.example.mylsp.data.model.api.assesment.KomponenGetReq>
)

data class KomponenGetReq(
    val id: Int,
    val ak03_submission_id:Int,
    val komponen_id: Int,
    val hasil: String,
    val catatan_asesi: String,
    val komponen: com.example.mylsp.data.model.api.assesment.KomponenData
)
