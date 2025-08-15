package com.example.mylsp.screen.asesor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IA09Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header biru
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(vertical = 16.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ASOSIASI LEZHAR RAHMA PANGESTU",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Content area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            // Logo dan judul section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Logo LSP
                Image(
                    painter = painterResource(id = R.drawable.hd),
                    contentDescription = "Logo LSP",
                    modifier = Modifier
                        .size(50.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Judul dan kode
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "FR.IA.06A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "VER : SERTIFIKAT KOMPETENSI TERSEBAR ASESOR",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Label Skema Sertifikasi
            Text(
                text = "Skema Sertifikasi",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Form fields dalam format tabel
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                IA09FormRow(
                    label = "Judul Unit",
                    value = "Okupasi Junior Custom-Made"
                )
                IA09FormRow(
                    label = "Kode Unit",
                    value = "LSK.FRI.03.001.01 (8-04/03/2023"
                )
                IA09FormRow(
                    label = "Unit",
                    value = "Standardized Template Report Mendiri"
                )
                IA09FormRow(
                    label = "Nama Asesor",
                    value = ""
                )
                IA09FormRow(
                    label = "Nama Asesi",
                    value = ""
                )
                IA09FormRow(
                    label = "Tanggal",
                    value = ""
                )
            }
        }

        // Divider sebelum tombol
        Divider(
            color = Color.LightGray,
            thickness = 1.dp
        )

        // Bottom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle Approve */ },
                modifier = Modifier
                    .width(100.dp)
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text(
                    text = "APPROVE",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { /* Handle Reject */ },
                modifier = Modifier
                    .width(100.dp)
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text(
                    text = "REJECT",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun IA09FormRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Label column
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .width(80.dp)
                .padding(end = 8.dp)
        )

        // Separator
        Text(
            text = ":",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )

        // Value column
        Text(
            text = if (value.isNotEmpty()) value else "",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IA09ScreenPreview() {
    IA09Screen()
}