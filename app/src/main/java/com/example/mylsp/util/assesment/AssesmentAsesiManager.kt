package com.example.mylsp.util.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AssesmentAsesiManager(context:Context) {
    private val prefs = EncryptedSharedPreferences.create(
        "secure_assesment_asesi_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    fun setAssesmentAsesiIdTemp(assesmentAsesiId: Int){
        prefs.edit(){
            putInt("assesment_asesi_id_temp", assesmentAsesiId)
        }
    }

    fun getAssesmentAsesiIdTemp(assesmentAsesiId: Int){
        prefs.getInt("assesment_asesi_id_temp", -1)
    }

    fun setAssesmentAsesiId(assesmentAsesiId: Int){
        prefs.edit(){
            putInt("assesment_asesi_id", assesmentAsesiId)
        }
    }

    fun getAssesmentId():Int{
        return prefs.getInt("assesment_asesi_id", -1)
    }

    fun clear(){
        prefs.edit().clear()
    }
}