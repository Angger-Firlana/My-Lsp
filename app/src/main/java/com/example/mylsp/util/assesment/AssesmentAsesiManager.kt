package com.example.mylsp.util.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.asesi.AssesmentAsesi
import com.google.gson.Gson

class AssesmentAsesiManager(context: Context) {
    private val gson = Gson()

    private val prefs = EncryptedSharedPreferences.create(
        "secure_assesment_asesi_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getAssesmentAsesiIdTemp(): Int {
        return prefs.getInt("assesment_asesi_id_temp", -1)
    }

    fun setAssesmentAsesiId(assesmentAsesiId: Int) {
        prefs.edit {
            putInt("assesment_asesi_id", assesmentAsesiId)
        }
    }

    fun saveAssesmentAsesi(assesmentAsesi: AssesmentAsesi) {
        val json = gson.toJson(assesmentAsesi)
        prefs.edit {
            putString("assesment_asesi", json)
            putInt("assesment_asesi_id", assesmentAsesi.id) // simpan id juga biar gampang akses
        }
    }

    fun getAssesmentAsesi(): AssesmentAsesi? {
        val json = prefs.getString("assesment_asesi", null)
        return json?.let {
            gson.fromJson(it, AssesmentAsesi::class.java)
        }
    }

    fun getAssesmentId(): Int {
        return prefs.getInt("assesment_asesi_id", -1)
    }

    fun clear() {
        prefs.edit().clear()
    }
}
