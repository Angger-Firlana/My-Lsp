package com.example.mylsp.data.local.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylsp.data.api.assesment.KomponenData
import com.example.mylsp.data.model.api.assesment.KomponenPostReq
import com.google.gson.Gson

class AK03SubmissionManager(context: Context) {
    private val gson = Gson()

    private val prefs = EncryptedSharedPreferences.create(
        "secure_AK03Submission_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAK03Submission(
        assesment_asesi_id: Int,
        komponen: com.example.mylsp.data.api.assesment.KomponenData,
        hasil: String,
        catatanAsesi:String
    ) {
        val allSubmission = getAllSubmissionAK03(assesment_asesi_id).toMutableList()
        val indexKomponen = allSubmission.indexOfFirst {
            it.komponen_id == komponen.id
        }



        if (indexKomponen >= 0){
            val currentKomponen = allSubmission[indexKomponen]
            allSubmission[indexKomponen] = currentKomponen.copy(
                hasil = hasil.lowercase(),
                catatan_asesi = catatanAsesi
            )
        }else{
            val newKomponen = com.example.mylsp.data.model.api.assesment.KomponenPostReq(
                komponen_id = komponen.id,
                hasil = hasil.lowercase(),
                catatan_asesi = catatanAsesi
            )
            allSubmission.add(newKomponen)
        }

        saveAllSubmission(assesment_asesi_id, allSubmission)
    }

    fun getAllSubmissionAK03(assesment_asesi_id: Int):List<com.example.mylsp.data.model.api.assesment.KomponenPostReq>{
        val json = prefs.getString("AK03Submission_$assesment_asesi_id", null)
        return json?.let {
            val array = gson.fromJson(it, Array<com.example.mylsp.data.model.api.assesment.KomponenPostReq>::class.java)
            array.toList()
        }?:run {
            emptyList()
        }
    }

    fun saveAllSubmission(assesment_asesi_id: Int, listKomponen:List<com.example.mylsp.data.model.api.assesment.KomponenPostReq>){
        val json = gson.toJson(listKomponen)
        prefs.edit{
            putString("AK03Submission_$assesment_asesi_id",json)
        }
    }
}