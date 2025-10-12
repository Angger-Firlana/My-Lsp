package com.example.mylsp.data.local.assesment

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.data.api.assesment.AK04
import com.example.mylsp.data.api.assesment.SubmissionQuestion
import com.google.gson.Gson

class Ak04SubmissionManager(context: Context) {

    private val gson = Gson()

    private val prefs = EncryptedSharedPreferences.create(
        "secure_form_ak04_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAnswer(
        assesmentAsesiId: Int,
        questionId: Int,
        selectedOption: String
    ) {
        val allSubmissions = getAllSubmissions(assesmentAsesiId).toMutableList()

        // update / replace jawaban
        val index = allSubmissions.indexOfFirst { it.ak04_question_id == questionId }
        val updated = SubmissionQuestion(
            ak04_question_id = questionId,
            selected_option = selectedOption
        )

        if (index >= 0) {
            allSubmissions[index] = updated
        } else {
            allSubmissions.add(updated)
        }

        saveAllSubmissions(assesmentAsesiId, allSubmissions)
        Log.d("Ak04Manager", "Saved Q$questionId -> $selectedOption")
    }

    fun saveReason(assesmentAsesiId: Int, alasan: String) {
        prefs.edit {
            putString("ak04_reason_$assesmentAsesiId", alasan)
        }
    }

    fun getReason(assesmentAsesiId: Int): String {
        return prefs.getString("ak04_reason_$assesmentAsesiId", "") ?: ""
    }

    fun getAllSubmissions(assesmentAsesiId: Int): List<SubmissionQuestion> {
        val json = prefs.getString("ak04_submissions_$assesmentAsesiId", null)
        return json?.let {
            val array = gson.fromJson(it, Array<SubmissionQuestion>::class.java)
            array.toList()
        } ?: emptyList()
    }

    private fun saveAllSubmissions(assesmentAsesiId: Int, submissions: List<SubmissionQuestion>) {
        val json = gson.toJson(submissions)
        prefs.edit {
            putString("ak04_submissions_$assesmentAsesiId", json)
        }
    }

    fun clear(assesmentAsesiId: Int) {
        prefs.edit {
            remove("ak04_submissions_$assesmentAsesiId")
            remove("ak04_reason_$assesmentAsesiId")
        }
    }

    fun buildRequest(assesmentAsesiId: Int): AK04 {
        return AK04(
            assesment_asesi_id = assesmentAsesiId,
            alasan_banding = getReason(assesmentAsesiId),
            questions = getAllSubmissions(assesmentAsesiId)
        )
    }
}
