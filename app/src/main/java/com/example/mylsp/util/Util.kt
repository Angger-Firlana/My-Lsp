package com.example.mylsp.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.mylsp.model.api.assesment.BuktiRelevanApl02
import com.example.mylsp.model.api.SubmissionGroup

object Util {
    var jawabanApl02 = mutableStateOf(
        SubmissionGroup(assesmentAsesiId = 0, submissions = listOf())
    )
    var buktiRelevanApl02 = mutableStateListOf<BuktiRelevanApl02>()
    var logUser = 0
}