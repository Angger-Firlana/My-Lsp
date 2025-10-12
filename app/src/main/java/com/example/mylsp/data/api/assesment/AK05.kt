package com.example.mylsp.data.api.assesment

import com.google.gson.annotations.SerializedName

// Request untuk submit AK05
data class Ak05SubmissionRequest(
    @SerializedName("assesment_asesi_id")
    val assesmentAsesiId: Int,
    val keputusan: String,
    val keterangan: String? = null,
    @SerializedName("aspek_positif")
    val aspekPositif: String? = null,
    @SerializedName("aspek_negatif")
    val aspekNegatif: String? = null,
    @SerializedName("penolakan_hasil")
    val penolakanHasil: String? = null,
    @SerializedName("saran_perbaikan")
    val saranPerbaikan: String? = null,
    @SerializedName("ttd_asesor")
    val ttdAsesor: String
)

// Response setelah submit AK05
data class Ak05SubmissionResponse(
    val success: Boolean,
    val message: String,
    @SerializedName("submission_id")
    val submissionId: Int? = null
)

// Response untuk getAk05ByAssesi
data class GetAk05Response(
    val success: Boolean,
    val message: String,
    val data: List<Ak05SubmissionData>
)

data class Ak05SubmissionData(
    val id: Int,
    @SerializedName("assesment_asesi_id")
    val assesmentAsesiId: Int,
    val keputusan: String,
    val keterangan: String?,
    @SerializedName("aspek_positif")
    val aspekPositif: String?,
    @SerializedName("aspek_negatif")
    val aspekNegatif: String?,
    @SerializedName("penolakan_hasil")
    val penolakanHasil: String?,
    @SerializedName("saran_perbaikan")
    val saranPerbaikan: String?,
    @SerializedName("ttd_asesor")
    val ttdAsesor: String,
    @SerializedName("assesment_asesi")
    val assesmentAsesi: AssesmentAsesiData
)

data class AssesmentAsesiData(
    val id: Int,
    @SerializedName("assesment_id")
    val assesmentId: Int,
    @SerializedName("assesi_id")
    val assesiId: Int,
    val status: String,
    val asesi: AsesiData
)

data class AsesiData(
    val id: Int,
    val nama: String
)
