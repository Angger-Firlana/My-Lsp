package com.example.mylsp.util

import com.example.mylsp.model.api.BuktiSubmission
import com.example.mylsp.model.api.ElemenSubmission
import com.example.mylsp.model.api.JawabanApl02
import com.example.mylsp.model.api.Submission
import com.example.mylsp.model.api.SubmissionGroup

class JawabanManager {

    /**
     * Upsert per elemen.
     * - Merge berdasarkan (unitKe, kodeUnit, elemenId)
     * - Hanya simpan elemen jika lengkap: kompetensinitas != "" && buktiList.isNotEmpty()
     */
    fun upsertElemen(
        skemaId: Int,
        unitKe: Int,
        kodeUnit: String,
        elemenId: Int,
        kompetensinitas: String? = null,
        buktiList: List<String>? = null
    ) {
        val currentGroup = Util.jawabanApl02.value
        val submissions = currentGroup.submissions.toMutableList()

        // cari submission per unit
        val subIndex = submissions.indexOfFirst { it.unit_ke == unitKe && it.kode_unit == kodeUnit }
        val submission = if (subIndex >= 0) submissions[subIndex]
        else Submission(unit_ke = unitKe, kode_unit = kodeUnit, elemen = emptyList())

        // cari elemen
        val elems = submission.elemen.toMutableList()
        val elemIndex = elems.indexOfFirst { it.elemen_id == elemenId }
        val existingElem = if (elemIndex >= 0) elems[elemIndex]
        else ElemenSubmission(elemen_id = elemenId, kompetensinitas = "", bukti_yang_relevan = emptyList())

        // tentukan nilai baru
        val newKom = kompetensinitas ?: existingElem.kompetensinitas
        val newBukti = buktiList?.map { BuktiSubmission(it) } ?: existingElem.bukti_yang_relevan

        // hanya simpan kalau lengkap
        val complete = newKom.isNotBlank() && newBukti.isNotEmpty()
        if (complete) {
            val updatedElem = existingElem.copy(
                kompetensinitas = newKom,
                bukti_yang_relevan = newBukti
            )
            if (elemIndex >= 0) elems[elemIndex] = updatedElem else elems.add(updatedElem)
        } else {
            // kalau belum lengkap, pastikan elemen tidak ikut terkirim
            if (elemIndex >= 0) elems.removeAt(elemIndex)
        }

        val updatedSubmission = submission.copy(elemen = elems)
        if (elems.isEmpty()) {
            if (subIndex >= 0) submissions.removeAt(subIndex)
        } else {
            if (subIndex >= 0) submissions[subIndex] = updatedSubmission else submissions.add(updatedSubmission)
        }

        Util.jawabanApl02.value = SubmissionGroup(
            skema_id = if (currentGroup.skema_id == 0) skemaId else currentGroup.skema_id,
            submissions = submissions
        )
    }
}