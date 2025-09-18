package com.example.mylsp.model.api.asesi

import com.example.mylsp.model.api.Asesi

data class PostAssesmentAsesiReq(
    val assesment_id: Int,
    val assesi_id:Int
)

data class AssesmentAsesiResponse(
    val success: Boolean,
    val message: String,
    val data: List<AssesmentAsesi>
)

data class AssesmentAsesi(
    val id: Int,
    val assesment_id: Int,
    val assesi_id: Int,
    val created_at: String,
    val updated_at: String,
    val asesi:Asesi
)

data class PostAssesmentAsesiResponse(
    val success: Boolean,
    val message: String,
    val data: AssesmentAsesi
)