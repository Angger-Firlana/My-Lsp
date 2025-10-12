package com.example.mylsp.data.api.asesi

import com.example.mylsp.model.api.Apl01
import com.example.mylsp.model.api.Asesi
import com.example.mylsp.data.api.assesment.AsesiData

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
    val status: String,
    val created_at: String,
    val updated_at: String,
    val asesi:Apl01
)

data class PatchStatusReq(
    val status: String
)

data class PatchStatusResponse(
    val success: Boolean,
    val message: String,
    val data: DataPatchStatusReponse
)

data class DataPatchStatusReponse(
    val id:Int,
    val assesment_id: Int,
    val assesi_id: Int,
    val status: String,
    val assesment: AssesmentAsesi,
    val assesi: Asesi
)

data class PostAssesmentAsesiResponse(
    val success: Boolean,
    val message: String,
    val data: AssesmentAsesi? = null
)