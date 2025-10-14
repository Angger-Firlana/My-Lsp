package com.example.mylsp.ui.screen.asesor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.util.AppFont

@Composable
fun UploadSoal(modifier: Modifier = Modifier) {
    var fileName by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Upload Soal",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = AppFont.Poppins
                )

                Spacer(modifier = Modifier.height(16.dp))

                CardUploadSoal(
                    fileName = fileName,
                    onClick = {
                        fileName = "Soal_Ujian_2024.pdf"
                    }
                )

                Spacer(Modifier.height(8.dp))

                ButtonSave(
                    enabled = fileName != null,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun CardUploadSoal(fileName: String?, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (fileName != null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (fileName != null) Icons.Default.Check else Icons.Default.AttachFile,
                    contentDescription = null,
                    tint = if (fileName != null) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (fileName != null) "File sudah terupload" else "Masukkan file word soal anda",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = if (fileName != null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (fileName != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = fileName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonSave(modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Simpan Soal",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins
            )
        }
    }
}