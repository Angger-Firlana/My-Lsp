package com.example.mylsp.data.local.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@Suppress("DEPRECATION")
class AssessmentManager(context: Context) {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "assessment_data",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAssessmentId(
        id:Int
    ) {
        sharedPreferences.edit {
            putInt("assessment_id", id)
        }
    }
}