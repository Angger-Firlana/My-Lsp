package com.example.mylsp.util

import com.example.mylsp.model.api.JawabanApl02

class JawabanManager {

    fun addJawaban(idElemen: Int, jawaban: String) {
        val list = Util.jawabanApl02
        val existing = list.find { it.idElemen == idElemen }
        if (existing != null) {
            existing.jawaban = jawaban
        } else {
            list.add(JawabanApl02(idElemen, jawaban))
        }
    }

    fun addBukti(idElemen: Int,bukti: String) {
        val list = Util.buktiRelevanApl02
        val existing = list.find { it.idElemen == idElemen }
        if (existing != null) {
                if (!existing.buktiRelevans.contains(bukti)) {
                    existing.buktiRelevans.add(bukti)
                }


        }
    }

    fun removeBukti(idElemen: Int, bukti: String) {
        val existing = Util.buktiRelevanApl02.find { it.idElemen == idElemen }
        existing?.buktiRelevans?.remove(bukti)
    }
}
