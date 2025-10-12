package com.example.mylsp.data.api.assesment

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

data class GetAK03Response(
    val success: Boolean,
    val message: String,
    val data: List<GetAK03Data>
)

data class GetAK03Data(
    val id: Int,
    val assesment_asesi_id: Int,
    val catatan_tambahan: String,
    val details: List<KomponenGetReq>
)

data class KomponenGetReq(
    val id: Int,
    val ak03_submission_id:Int,
    val komponen_id: Int,
    val hasil: String,
    val catatan_asesi: String,
    val komponen: KomponenData
)
