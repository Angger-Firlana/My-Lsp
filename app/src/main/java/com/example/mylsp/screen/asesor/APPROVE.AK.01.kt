package com.example.mylsp.screen.asesor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun ApproveAK01Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 🔵 Header Biru
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "AFDHAL EZHAR RAHMA PANGESTU",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🟠 Logo + Judul
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.hd),
                contentDescription = "Logo LSP",
                modifier = Modifier.size(60.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "FR.IA.01. CL",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    "CEKLIS OBSERVASI AKTIVITAS DI TEMPAT KERJA ATAU TEMPAT KERJA SIMULASI",
                    fontSize = 10.sp,
                    fontFamily = AppFont.Poppins
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🟠 Garis oranye
        OrangeDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Skema Sertifikasi (hanya judul & kode unit)
        Text(
            "Skema Sertifikasi",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            Text("Judul Skema", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Spacer(modifier = Modifier.width(8.dp))
            Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Pemrograman Android", fontSize = 12.sp, fontFamily = AppFont.Poppins)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            Text("Kode Unit", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Spacer(modifier = Modifier.width(22.dp))
            Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Spacer(modifier = Modifier.width(8.dp))
            Text("J.620100.004.02", fontSize = 12.sp, fontFamily = AppFont.Poppins)
        }

        Spacer(modifier = Modifier.height(8.dp))
        OrangeDivider()

        Spacer(modifier = Modifier.height(12.dp))

        // Kelompok Pekerjaan
        Text(
            "Kelompok Pekerjaan 1",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(modifier = Modifier.padding(start = 24.dp, top = 4.dp)) {
            Text("1. J.620100.004.02 Menggunakan Struktur Data", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Text("2. dst", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Text("3. dst", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            Text("4. dst", fontSize = 12.sp, fontFamily = AppFont.Poppins)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Unit Kompetensi
        Text(
            "Unit Kompetensi 1",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text("Kode Unit : J.620100.004.02", fontSize = 12.sp, fontFamily = AppFont.Poppins, modifier = Modifier.padding(horizontal = 16.dp))
        Text("Judul Unit : Menggunakan Struktur Data", fontSize = 12.sp, fontFamily = AppFont.Poppins, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(8.dp))
        OrangeDivider()

        Spacer(modifier = Modifier.height(12.dp))

        // Card Elemen
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp)), // 🔲 garis hitam
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    "Elemen 1: Mengidentifikasi konsep data dan struktur data",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    "Kriteria Untuk Kerja",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins
                )
                Spacer(modifier = Modifier.height(8.dp))

                CriteriaRow("Mengidentifikasi konsep data dan struktur data sesuai dengan konteks")
                CriteriaRow("Membandingkan alternatif struktur data kelebihan dan kekurangannya untuk konteks permasalahan yang diselesaikan")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OrangeDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // Kotak abu-abu Penilaian Lanjutan
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Penilaian Lanjutan",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = AppFont.Poppins,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Garis pemisah abu-abu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        // Tombol Approve / Reject
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(120.dp)
            ) {
                Text("APPROVE", fontFamily = AppFont.Poppins)
            }
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(120.dp)
            ) {
                Text("REJECT", fontFamily = AppFont.Poppins)
            }
        }
    }
}

@Composable
fun CriteriaRow(text: String) {
    var checkedYes by remember { mutableStateOf(false) }
    var checkedNo by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            fontFamily = AppFont.Poppins
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ya", fontSize = 10.sp, fontFamily = AppFont.Poppins)
            Checkbox(checked = checkedYes, onCheckedChange = { checkedYes = it })
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Tidak", fontSize = 10.sp, fontFamily = AppFont.Poppins)
            Checkbox(checked = checkedNo, onCheckedChange = { checkedNo = it })
        }
    }
}

@Composable
fun OrangeDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(Color(0xFFFF9800))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewApproveAK01() {
    ApproveAK01Screen()
}
