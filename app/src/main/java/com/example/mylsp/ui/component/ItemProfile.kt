package com.example.mylsp.ui.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ItemProfile(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)
