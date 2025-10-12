package com.example.mylsp.data.local.user

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.core.content.edit

@Suppress("DEPRECATION")
class TokenManager(context:Context) {
    private val sharedPrefs = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String) {
        sharedPrefs.edit(commit = true) {
            putString("token", token)
        }
    }

    fun getToken(): String? {
        return sharedPrefs.getString("token", null)
    }

    fun clearToken() {
        sharedPrefs.edit(commit = true) {
            remove("token")
        }
    }
}