package com.example.mylsp.util.user

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.Asesi
import com.google.gson.Gson

class AsesiManager(context: Context) {

    private val gson = Gson()

    private val sharedPrefs = EncryptedSharedPreferences.create(
        "secure_asesi_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun getKey(asesiId: Int) = "asesi_$asesiId"

    fun saveAsesi(asesi: Asesi) {
        val json = gson.toJson(asesi)
        sharedPrefs.edit().putString(getKey(asesi.id), json).apply()
    }

    fun getAsesi(asesiId: Int): Asesi? {
        val json = sharedPrefs.getString(getKey(asesiId), null)
        return if (json != null) gson.fromJson(json, Asesi::class.java) else null
    }

    fun deleteAsesi(asesiId: Int) {
        sharedPrefs.edit().remove(getKey(asesiId)).apply()
    }

    fun getAllAsesi(): List<Asesi> {
        return sharedPrefs.all.mapNotNull { (_, value) ->
            (value as? String)?.let { gson.fromJson(it, Asesi::class.java) }
        }
    }
}
