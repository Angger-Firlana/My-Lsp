package com.example.mylsp.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.mylsp.model.api.BuktiRelevanApl02
import com.example.mylsp.model.api.JawabanApl02

object Util {
    var jawabanApl02 = mutableStateListOf<JawabanApl02>()
    var buktiRelevanApl02 = mutableStateListOf<BuktiRelevanApl02>()
    var logUser = 0
}