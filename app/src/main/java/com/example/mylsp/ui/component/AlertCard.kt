package com.example.mylsp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.common.enums.TypeAlert
import com.example.mylsp.util.AppFont

@Composable
fun AlertCard(message: String, type: TypeAlert) {
    val bgColor = when (type) {
        TypeAlert.Info -> Color(0xFFDCFCE7)
        TypeAlert.Warning -> Color(0xFFFEF3C7)
        else -> Color(0xFFFEE2E2)
    }
    val textColor = when (type) {
        TypeAlert.Info -> Color(0xFF166534)
        TypeAlert.Warning -> Color(0xFF92400E)
        else -> Color(0xFF991B1B)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                message,
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = textColor
            )
        }
    }
}