package com.example.mylsp.util.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.assesment.Ia01Submission
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

    fun saveSubmission(assesmentAsesiId: Int, submission: Ia01Submission) {
        val json = gson.toJson(submission)
        prefs.edit {
            putString("submission_$assesmentAsesiId", json)
        }
    }

    fun getSubmission(assesmentAsesiId: Int): Ia01Submission? {
        val json = prefs.getString("submission_$assesmentAsesiId", null)
        return json?.let {
            gson.fromJson(it, Ia01Submission::class.java)
        }
    }

    fun clearSubmission(assesmentAsesiId: Int) {
        prefs.edit {
            remove("submission_$assesmentAsesiId")
        }
    }
}
