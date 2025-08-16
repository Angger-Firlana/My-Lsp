package com.example.mylsp.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.mylsp.model.api.BuktiRelevanApl02
import com.example.mylsp.model.api.JawabanApl02
import com.example.mylsp.model.api.Submission
import com.example.mylsp.model.api.SubmissionGroup

object Util {
    var jawabanApl02 = mutableStateOf(
        SubmissionGroup(skema_id = 0, submissions = listOf())
    )
    var buktiRelevanApl02 = mutableStateListOf<BuktiRelevanApl02>()
    var logUser = 0
}