package com.example.mylsp.model

import com.example.lsp24.models.DokumenAsesmen
import com.example.lsp24.models.FormApl01
import com.example.lsp24.models.FormApl02
import com.example.lsp24.models.SkemaSertifikasi
import com.example.lsp24.models.TandaTangan
import com.example.lsp24.models.UnitKompetensiDetail
import com.google.gson.annotations.SerializedName

data class MukData(
    @SerializedName("skema_sertifikasi")
    val skemaSertifikasi: SkemaSertifikasi,

    @SerializedName("form_apl01")
    val formApl01: FormApl01,

    @SerializedName("form_apl02")
    val formApl02: List<FormApl02>,

    @SerializedName("unit_kompetensi_detail")
    val unitKompetensiDetail: List<UnitKompetensiDetail>,

    @SerializedName("dokumen_asesmen")
    val dokumenAsesmen: List<DokumenAsesmen>,

    @SerializedName("tanda_tangan")
    val tandaTangan: List<TandaTangan>,

)
