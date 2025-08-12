package com.example.mylsp.viewmodel

import com.example.mylsp.model.api.JawabanApl02
import com.example.mylsp.util.Util

class JawabanManager{
    fun addToList(jawabanApl02: JawabanApl02){
        val list = Util.jawabanApl02
        val jawaban = list.find { jawabanApl02.idElemen == it.idElemen }
        if (jawaban != null){
            jawaban.jawaban = jawabanApl02.jawaban
        }else{
            list.add(jawabanApl02)
        }
    }
}