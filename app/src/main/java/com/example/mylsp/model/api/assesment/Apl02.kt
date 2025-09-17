package com.example.mylsp.model.api.assesment

import com.example.mylsp.model.api.Jurusan

data class Apl02(
    val success: Boolean,
    val jurusan: Jurusan,
    val judul_skema: String,
    val nomor_skema: String,
    val data: List<UnitApl02>
)

data class UnitApl02(
    val unit_ke: Int,
    val kode_unit: String,
    val judul_unit:String,
    val elemen: Map<String, ElemenAPL02>
)

data class ElemenAPL02(
    val elemen_index: Int,
    val nama_elemen: String,
    val kuk: List<KriteriaUntukKerja>
)

data class KriteriaUntukKerja(
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