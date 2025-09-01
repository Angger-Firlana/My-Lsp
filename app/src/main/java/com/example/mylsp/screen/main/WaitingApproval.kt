package com.example.mylsp.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.util.AppFont

@Composable
fun WaitingApprovalScreen(
    modifier: Modifier = Modifier,
    nextStep: () -> Unit,
    backFillForm: () -> Unit,
    formName: String = "APL01", // default nama form
    type: String = "pendaftaran", // ✨ bisa "pendaftaran" atau "jawaban"
    status: String? = null,
    textButton: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon di atas
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Success",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Judul dinamis
            val title = when (status) {
                null -> if (type == "jawaban")
                    "Belum Mengisi Jawaban $formName"
                else
                    "Belum Mengisi Form $formName"

                "pending" -> if (type == "jawaban")
                    "Jawaban $formName Sedang Dicek"
                else
                    "Pendaftaran $formName Berhasil!"

                "accepted" -> if (type == "jawaban")
                    "Jawaban $formName Diterima!"
                else
                    "Pendaftaran $formName Diterima!"

                "rejected" -> if (type == "jawaban")
                    "Jawaban $formName Ditolak"
                else
                    "Pendaftaran $formName Ditolak"

                else -> "Status Tidak Dikenal"
            }

            Text(
                text = title,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card Status
            Card(
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Waiting",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Status: ${status ?: if (type == "jawaban") "Belum Mengisi" else "Belum Mengisi $formName"}",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFFFF9800)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol aksi
            when (status) {
                "accepted" -> {
                    Button(
                        onClick = nextStep,
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) { Text(textButton ?: "Lanjut ke Dashboard") }
                }
                "rejected", null -> {
                    Button(
                        onClick = backFillForm,
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(textButton ?: if (type == "jawaban") "Isi Ulang Jawaban" else "Isi Ulang Form $formName")
                    }
                }
            }
        }
    }
}
