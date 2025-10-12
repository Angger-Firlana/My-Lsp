package com.example.mylsp.data.local.assesment

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.assesment.*
import com.google.gson.Gson

class IA01SubmissionManager(context: Context) {

    private val gson = Gson()

    private val prefs = EncryptedSharedPreferences.create(
        "secure_form_ia01_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveIA01Answer(
        assesmentAsesiId: Int,
        unitKe: Int,
        kodeUnit: String,
        elemenId: Int,
        kukId: Int,
        hasilObservasi: String,
        catatanAsesor: String
    ) {
        // Ambil semua submissions untuk assesment ini
        val allSubmissions = getAllSubmissions(assesmentAsesiId).toMutableList()

        // Cari unit yang sesuai
        val unitIndex = allSubmissions.indexOfFirst {
            it.unit_ke == unitKe && it.kode_unit == kodeUnit
        }

        val currentUnit = if (unitIndex >= 0) {
            allSubmissions[unitIndex]
        } else {
            // Buat unit baru jika belum ada
            com.example.mylsp.data.api.assesment.IA01UnitSubmission(
                unit_ke = unitKe,
                kode_unit = kodeUnit,
                elemen = emptyList()
            )
        }

        // Update elemen dalam unit
        val updatedElemenList = currentUnit.elemen.toMutableList()
        val elemenIndex = updatedElemenList.indexOfFirst { it.elemen_id == elemenId }

        val currentElemen = if (elemenIndex >= 0) {
            updatedElemenList[elemenIndex]
        } else {
            com.example.mylsp.data.api.assesment.IA01ElemenSubmission(
                elemen_id = elemenId,
                kuk = emptyList()
            )
        }

        // Update KUK dalam elemen
        val updatedKukList = currentElemen.kuk.toMutableList()
        val kukIndex = updatedKukList.indexOfFirst { it.kuk_id == kukId }
        Log.d("IA01SubmissionManager", "kuk_id = $kukId")
        val updatedKuk = com.example.mylsp.data.api.assesment.IA01KUKSubmission(
            kuk_id = kukId,
            skkni = hasilObservasi,
            teks_penilaian = catatanAsesor
        )

        if (kukIndex >= 0) {
            updatedKukList[kukIndex] = updatedKuk
        } else {
            updatedKukList.add(updatedKuk)
        }

        // Rebuild struktur
        val updatedElemen = currentElemen.copy(kuk = updatedKukList)

        if (elemenIndex >= 0) {
            updatedElemenList[elemenIndex] = updatedElemen
        } else {
            updatedElemenList.add(updatedElemen)
        }

        val updatedUnit = currentUnit.copy(elemen = updatedElemenList)

        if (unitIndex >= 0) {
            allSubmissions[unitIndex] = updatedUnit
        } else {
            allSubmissions.add(updatedUnit)
        }

        // Simpan semua submissions
        saveAllSubmissions(assesmentAsesiId, allSubmissions)
    }

    fun getIA01Submission(assesmentAsesiId: Int): com.example.mylsp.data.api.assesment.IA01UnitSubmission? {
        return getAllSubmissions(assesmentAsesiId).firstOrNull()
    }

    fun getAllSubmissions(assesmentAsesiId: Int): List<com.example.mylsp.data.api.assesment.IA01UnitSubmission> {
        val json = prefs.getString("ia01_submissions_$assesmentAsesiId", null)
        return json?.let {
            val array = gson.fromJson(it, Array<com.example.mylsp.data.api.assesment.IA01UnitSubmission>::class.java)
            array.toList()
        } ?: emptyList()
    }

    fun saveAllSubmissions(assesmentAsesiId: Int, submissions: List<com.example.mylsp.data.api.assesment.IA01UnitSubmission>) {
        val json = gson.toJson(submissions)
        prefs.edit {
            putString("ia01_submissions_$assesmentAsesiId", json)
        }
    }

    fun getSubmissionForUnit(
        assesmentAsesiId: Int,
        unitKe: Int,
        kodeUnit: String
    ): com.example.mylsp.data.api.assesment.IA01UnitSubmission? {
        return getAllSubmissions(assesmentAsesiId).find {
            it.unit_ke == unitKe && it.kode_unit == kodeUnit
        }
    }

    fun clearSubmissions(assesmentAsesiId: Int) {
        prefs.edit {
            remove("ia01_submissions_$assesmentAsesiId")
        }
    }

    // Method untuk ambil jawaban KUK tertentu
    fun getKUKAnswer(
        assesmentAsesiId: Int,
        unitKe: Int,
        kodeUnit: String,
        elemenId: Int,
        kukId: Int
    ): com.example.mylsp.data.api.assesment.IA01KUKSubmission? {
        val unit = getSubmissionForUnit(assesmentAsesiId, unitKe, kodeUnit)
        val elemen = unit?.elemen?.find { it.elemen_id == elemenId }
        return elemen?.kuk?.find { it.kuk_id == kukId }
    }
}
