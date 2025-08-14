package com.example.mylsp.util

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@Suppress("DEPRECATION")
class UserManager(context: Context) {

    private val sharedPrefs = EncryptedSharedPreferences.create(
        "secure_user_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUser(
        id: String,
        name: String,
        email: String,
        role: String
    ) {
        sharedPrefs.edit {
            putString("user_id", id)
            putString("user_name", name)
            putString("user_email", email)
            putString("user_role", role)
        }
    }

    fun getUserId(): String? = sharedPrefs.getString("user_id", null)
    fun getUserName(): String? = sharedPrefs.getString("user_name", null)
    fun getUserEmail(): String? = sharedPrefs.getString("user_email", null)
    fun getUserRole(): String? = sharedPrefs.getString("user_role", null)

    fun clearUser() {
        sharedPrefs.edit {
            remove("user_id")
            remove("user_name")
            remove("user_email")
            remove("user_role")
        }
    }
}
