package com.example.mylsp.screen.asesor

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.HeaderForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AK05(modifier: Modifier = Modifier, navController: NavController) {
    var checkedItems by remember { mutableStateOf(mutableMapOf<String, String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AFDHAL EZHAR RAHMA PANGESTU",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            HeaderForm(
                "FR.IA.01. CL",
                "CEKLIS OBSERVASI AKTIVITAS DI TEMPAT KERJA ATAU TEMPAT KERJA SIMULASI"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red,
                thickness = 1.dp
            )

            Text(
                text = "Skema Sertifikasi",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Judul Unit :",
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = "Judul Unit :",
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kelompok Pekerjaan :",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)) {
                Text(text = "1. J.620000.004.02 Mengidentifikasi Struktur Data", fontSize = 12.sp)
                Text(text = "2. TIK", fontSize = 12.sp)
                Text(text = "3. dst", fontSize = 12.sp)
                Text(text = "4. dst", fontSize = 12.sp)
            }

            Text(
                text = "Unit Kompetensi :",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(text = "Kode Unit", fontSize = 12.sp, modifier = Modifier.width(80.dp))
                Text(text = ": J.620000.004.02", fontSize = 12.sp)
            }

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(text = "Judul Unit", fontSize = 12.sp, modifier = Modifier.width(80.dp))
                Text(text = ": Mengidentifikasi Struktur Data", fontSize = 12.sp)
            }


            Spacer(modifier = Modifier.height(16.dp))


            ChecklistSection(
                checkedItems = checkedItems,
                onCheckedChange = { key, value ->
                    checkedItems = checkedItems.toMutableMap().apply { put(key, value) }
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        text = "APPROVE",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        text = "REJECT",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ChecklistSection(
    checkedItems: Map<String, String>,
    onCheckedChange: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // garis kotak
            .padding(12.dp)
    ) {
        Text(
            text = "Elemen 1: Mengidentifikasi konsep data dan struktur data",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Kriteria Untuk Kerja",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Header Ya / Tidak
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "Ya",
                fontSize = 12.sp,
                modifier = Modifier.width(40.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "Tidak",
                fontSize = 12.sp,
                modifier = Modifier.width(40.dp),
                textAlign = TextAlign.Center
            )
        }

        // Checklist item
        ChecklistItem(
            text = "Mengidentifikasi konsep data dan struktur data sesuai dengan konteks",
            selectedValue = checkedItems["item1"] ?: "",
            onValueChange = { value -> onCheckedChange("item1", value) }
        )

        ChecklistItem(
            text = "Membandingkan alternatif struktur data kelebihan dan kekurangannya untuk konteks permasalahan yang diselesaikan",
            selectedValue = checkedItems["item2"] ?: "",
            onValueChange = { value -> onCheckedChange("item2", value) }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "penilaian lanjut...",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ChecklistItem(
    text: String,
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bullet + teks
        Text(
            text = "• $text",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        // Kolom Ya
        Checkbox(
            checked = selectedValue == "ya",
            onCheckedChange = { if (it) onValueChange("ya") else onValueChange("") },
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(24.dp))

        // Kolom Tidak
        Checkbox(
            checked = selectedValue == "tidak",
            onCheckedChange = { if (it) onValueChange("tidak") else onValueChange("") },
            modifier = Modifier.size(18.dp)
        )
    }
}



@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp",
    showSystemUi = true
)
@Composable
fun PreviewAK05() {
    MaterialTheme {
        Surface {
            AK05(navController = rememberNavController())
        }
    }
}