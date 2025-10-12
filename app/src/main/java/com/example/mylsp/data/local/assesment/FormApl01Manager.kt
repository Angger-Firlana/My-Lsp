package com.example.mylsp.data.local.assesment

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class FormApl01Manager(context: Context) {

    private val prefs = EncryptedSharedPreferences.create(
        "secure_form_apl01_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveForm(
        userId: String?,
        namaLengkap: String?,
        noKtp: String?,
        tanggalLahir: String?,
        tempatLahir: String?,
        jenisKelamin: String?,
        kebangsaan: String?,
        alamatRumah: String?,
        kodePos: String?,
        noTeleponRumah: String?,
        noTeleponKantor: String?,
        noTelepon: String?,
        email: String?,
        kualifikasiPendidikan: String?,
        namaInstitusi: String?,
        jabatan: String?,
        alamatKantor: String?,
        kodePosKantor: String?,
        faxKantor: String?,
        emailKantor: String?,
        status: String?
    ) {
        prefs.edit {
            putString("user_id", userId)
            putString("nama_lengkap", namaLengkap)
            putString("no_ktp", noKtp)
            putString("tanggal_lahir", tanggalLahir)
            putString("tempat_lahir", tempatLahir)
            putString("jenis_kelamin", jenisKelamin)
            putString("kebangsaan", kebangsaan)
            putString("alamat_rumah", alamatRumah)
            putString("kode_pos", kodePos)
            putString("no_telepon_rumah", noTeleponRumah)
            putString("no_telepon_kantor", noTeleponKantor)
            putString("no_telepon", noTelepon)
            putString("email", email)
            putString("kualifikasi_pendidikan", kualifikasiPendidikan)
            putString("nama_institusi", namaInstitusi)
            putString("jabatan", jabatan)
            putString("alamat_kantor", alamatKantor)
            putString("kode_pos_kantor", kodePosKantor)
            putString("fax_kantor", faxKantor)
            putString("email_kantor", emailKantor)
            putString("status", status)
        }
    }

    fun getForm(): Map<String, String?> = mapOf(
        "user_id" to prefs.getString("user_id", null),
        "nama_lengkap" to prefs.getString("nama_lengkap", null),
        "no_ktp" to prefs.getString("no_ktp", null),
        "tanggal_lahir" to prefs.getString("tanggal_lahir", null),
        "tempat_lahir" to prefs.getString("tempat_lahir", null),
        "jenis_kelamin" to prefs.getString("jenis_kelamin", null),
        "kebangsaan" to prefs.getString("kebangsaan", null),
        "alamat_rumah" to prefs.getString("alamat_rumah", null),
        "kode_pos" to prefs.getString("kode_pos", null),
        "no_telepon_rumah" to prefs.getString("no_telepon_rumah", null),
        "no_telepon_kantor" to prefs.getString("no_telepon_kantor", null),
        "no_telepon" to prefs.getString("no_telepon", null),
        "email" to prefs.getString("email", null),
        "kualifikasi_pendidikan" to prefs.getString("kualifikasi_pendidikan", null),
        "nama_institusi" to prefs.getString("nama_institusi", null),
        "jabatan" to prefs.getString("jabatan", null),
        "alamat_kantor" to prefs.getString("alamat_kantor", null),
        "kode_pos_kantor" to prefs.getString("kode_pos_kantor", null),
        "fax_kantor" to prefs.getString("fax_kantor", null),
        "email_kantor" to prefs.getString("email_kantor", null),
        "status" to prefs.getString("status", null)
    )

    fun clear() {
        prefs.edit {
            clear()
        }
    }
}
