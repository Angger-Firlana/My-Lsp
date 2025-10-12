package com.example.mylsp.data.local.user

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.Apl01
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

    fun setId(asesiId: Int){
        sharedPrefs.edit {
            putInt("asesi_id", asesiId)
        }
    }

    fun getId():Int{
        return sharedPrefs.getInt("asesi_id", 0)
    }

    private fun getKey(asesiId: Int) = "asesi_$asesiId"

    fun saveAsesi(apl01: Apl01, asesiId: Int) {
        val json = gson.toJson(apl01)
        sharedPrefs.edit { putString(getKey(asesiId), json) }
    }

    fun getAsesi(asesiId: Int): Apl01? {
        val json = sharedPrefs.getString(getKey(asesiId), null)
        return if (json != null) gson.fromJson(json, Apl01::class.java) else null
    }

    fun deleteAsesi(asesiId: Int) {
        sharedPrefs.edit().remove(getKey(asesiId)).apply()
    }

    fun getAllAsesi(): List<Apl01> {
        return sharedPrefs.all.mapNotNull { (_, value) ->
            (value as? String)?.let { gson.fromJson(it, Apl01::class.java) }
        }
    }
    fun clear() {
        sharedPrefs.edit().clear().apply()
    }
}
