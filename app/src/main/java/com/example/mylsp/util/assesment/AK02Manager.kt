package com.example.mylsp.util.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.model.api.assesment.Ak02Bukti
import com.example.mylsp.model.api.assesment.Ak02Unit
import com.google.gson.Gson

class AK02Manager(context: Context) {
    private val prefs = EncryptedSharedPreferences.create(
        "assessment_data",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val gson = Gson()

    fun saveAK02Submission(
        assesmentAsesiId: Int,
        unitId: Int,
        bukti: String,
        isChecked: Boolean
    ): Result<String> {
        return try {
            val listUnits = getAK02Submission(assesmentAsesiId).toMutableList()
            val currentIndexUnit = listUnits.indexOfFirst { it.unit_id == unitId }

            val currentListBukti = if (currentIndexUnit != -1) {
                val currentUnit = listUnits[currentIndexUnit]
                currentUnit.bukti_yang_relevan
            } else {
                emptyList()
            }.toMutableList()

            val existingBuktiIndex = currentListBukti.indexOfFirst {
                it.bukti_description == bukti
            }

            if (isChecked) {
                // Add bukti if not exists
                if (existingBuktiIndex == -1) {
                    currentListBukti.add(
                        Ak02Bukti(bukti_description = bukti)
                    )
                }
            } else {
                // Remove bukti if exists
                if (existingBuktiIndex != -1) {
                    currentListBukti.removeAt(existingBuktiIndex)
                }
            }

            // Update or add unit
            if (currentIndexUnit != -1) {
                val unit = listUnits[currentIndexUnit]
                listUnits[currentIndexUnit] = unit.copy(
                    bukti_yang_relevan = currentListBukti
                )
            } else {
                listUnits.add(
                    Ak02Unit(
                        unit_id = unitId,
                        bukti_yang_relevan = currentListBukti
                    )
                )
            }

            setAK02Submission(assesmentAsesiId, listUnits)

            Result.success("Data berhasil disimpan")

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun setAK02Submission(
        assesmentAsesiId: Int,
        units: List<Ak02Unit>
    ) {
        val json = gson.toJson(units)
        prefs.edit {
            putString("AK02Submission_$assesmentAsesiId", json)
        }
    }

    fun getAK02Submission(
        assesmentAsesiId: Int
    ): List<Ak02Unit> {
        val json = prefs.getString("AK02Submission_$assesmentAsesiId", null)
        return json?.let {
            val array = gson.fromJson(json, Array<Ak02Unit>::class.java)
            array.toList()
        } ?: emptyList()
    }
}