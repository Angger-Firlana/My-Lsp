package com.example.mylsp.util

import androidx.compose.runtime.mutableStateOf
import com.example.lsp24.models.User

object Util {
    val dummyUsers = listOf(
        User(
            idUser = 1,
            username = "johndoe",
            passwordHash = "1234",
            email = "johndoe@example.com",
            role = "asesor",
            idRelated = null,
            lastLogin = "2025-08-06T08:30:00",
            isActive = true,
            resetToken = null,
            resetTokenExpires = null,
            createdAt = "2025-01-15T10:00:00",
            updatedAt = "2025-08-01T12:00:00"
        ),
        User(
            idUser = 2,
            username = "janedoe",
            passwordHash = "1234",
            email = "janedoe@example.com",
            role = "asesi",
            idRelated = 101,
            lastLogin = "2025-08-05T19:15:00",
            isActive = true,
            resetToken = "abc123reset",
            resetTokenExpires = "2025-08-10T00:00:00",
            createdAt = "2025-02-01T09:45:00",
            updatedAt = "2025-08-03T14:20:00"
        ),
        User(
            idUser = 3,
            username = "michael",
            passwordHash = "1234",
            email = "michael@example.com",
            role = "asesi",
            idRelated = 202,
            lastLogin = null,
            isActive = false,
            resetToken = null,
            resetTokenExpires = null,
            createdAt = "2025-03-12T16:10:00",
            updatedAt = null
        )
    )

    var logUser = 0

}