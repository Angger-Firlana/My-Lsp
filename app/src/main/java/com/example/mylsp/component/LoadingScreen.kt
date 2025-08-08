package com.example.mylsp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mylsp.util.AppFont
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tunggu Sebentar",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        var progress by remember { mutableStateOf(0f) }

        LaunchedEffect(Unit) {
            while (true) {
                progress += 0.01f
                if (progress > 1f) progress = 0f
                delay(10)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFEAF2F5))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFFFA74B))
            )
        }
    }
}
