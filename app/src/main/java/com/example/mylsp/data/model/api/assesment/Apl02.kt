package com.example.mylsp.data.model.api.assesment

import com.example.mylsp.model.api.Jurusan

data class Apl02(
    val success: Boolean,
    val jurusan: Jurusan,
    val data: com.example.mylsp.data.model.api.assesment.DataApl02
)
data class DataApl02(
    val id: Int,
    val judul_skema: String,
    val nomor_skema: String,
    val created_at: String,
    val updated_at: String,
    val units: List<com.example.mylsp.data.model.api.assesment.UnitApl02>
)

data class GetAPL02Response(
    val status: Boolean,
    val message: String,
    val data: List<com.example.mylsp.data.model.api.assesment.UnitGetResponse>
)

data class UnitGetResponse(
    val id: Int,
    val submission_date: String,
    val created_at: String,
    val updated_at: String,
    val assesment_asesi_id: Int,
    val ttd_assesor: String,
    val details: List<com.example.mylsp.data.model.api.assesment.UnitGetResponse.Detail>,
    val assesment_asesi: _root_ide_package_.com.example.mylsp.data.model.api.assesment.UnitGetResponse.AssesmentAsesi
) {
    data class Detail(
        val id: Int,
        val submission_id: Int,
        val unit_ke: Int,
        val kode_unit: String,
        val elemen_id: Int,
        val kompetensinitas: String,
        val created_at: String,
        val updated_at: String,
        val attachments: List<_root_ide_package_.com.example.mylsp.data.model.api.assesment.UnitGetResponse.Detail.Attachment>
    ) {
        data class Attachment(
            val id: Int,
            val submission_detail_id: Int,
            val bukti_id: Int,
            val created_at: String,
            val updated_at: String,
            val bukti: _root_ide_package_.com.example.mylsp.data.model.api.assesment.UnitGetResponse.Detail.Attachment.Bukti
        ) {
            data class Bukti(
                val id: Int,
                val assesi_id: Int,
                val nama_dokumen: String,
                val file_path: String,
                val description: String,
                val created_at: String,
                val updated_at: String
            )
        }
    }

    data class AssesmentAsesi(
        val id: Int,
        val assesment_id: Int,
        val assesi_id: Int,
        val status: String,
        val created_at: String,
        val updated_at: String
    )
}




data class Apl02Response(
    val success: Boolean,
    val jurusan: Jurusan,
    val judul_skema: String,
    val nomor_skema: String,
    val data: List<_root_ide_package_.com.example.mylsp.data.model.api.assesment.UnitApl02>
)

data class UnitApl02(
    val id:Int,
    val unit_ke: Int,
    val schema_id: Int,
    val kode_unit: String,
    val judul_unit:String,
    val elements: List<_root_ide_package_.com.example.mylsp.data.model.api.assesment.ElemenAPL02>
)

data class ElemenAPL02(
    val id:Int,
    val unit_id:Int,
    val elemen_index: Int,
    val nama_elemen: String,
    val kriteria_untuk_kerja: List<_root_ide_package_.com.example.mylsp.data.model.api.assesment.KriteriaUntukKerja>
)

data class KriteriaUntukKerja(
    val id:Int,
    val element_id: Int,
    val urutan: String,
    val deskripsi_kuk: String
)

data class JawabanApl02(
    val idElemen: Int,
    var jawaban: String
)

data class BuktiRelevanApl02(
    val idElemen: Int,
    val buktiRelevans: MutableList<String> = mutableListOf()
)

data class PostApproveRequest(
    val ttd_assesor: String
)

data class PostApproveResponse(
    val success: Boolean,
    val message: String
)