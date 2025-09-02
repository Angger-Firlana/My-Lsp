// File: PenilaianAsesiRekomendasi.kt
package com.example.mylsp.screen.asesor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CropFree
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenilaianAsesiRekomendasiScreen() {
    var kompeten by remember { mutableStateOf(false) }
    var belumKompeten by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AFDHAL EZHAR RAHMA PANGESTU",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Nama Mata Uji Kompetensi
            Text(
                text = "Nama Mata Uji Kompetensi: USK PRL - Pemrograman Dasar",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Daftar Dokumen Sertifikasi with button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Dokumen Sertifikasi",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Lengkap",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Data Diri with button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Data Diri",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Lengkap",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Divider line
            Divider(
                color = Color(0xFF2196F3),
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Program info
            Text(
                text = "Program: Rekayasa Perangkat Lunak",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Kelas info
            Text(
                text = "Kelas: 11",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // NISN info
            Text(
                text = "NISN: 076231233",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Email info
            Text(
                text = "email: afdhal322@gmail.com",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Rekomendasi title
            Text(
                text = "Rekomendasi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // Checkbox options
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Kompeten checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomCheckBox(
                        checked = kompeten,
                        onCheckedChange = {
                            kompeten = it
                            if (it) belumKompeten = false
                        }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Kompeten",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Belum Kompeten checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomCheckBox(
                        checked = belumKompeten,
                        onCheckedChange = {
                            belumKompeten = it
                            if (it) kompeten = false
                        }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Belum Kompeten",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Kirim button - positioned at bottom right
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "Kirim",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(3.dp))
            .background(if (checked) Color(0xFF2196F3) else Color.Transparent, RoundedCornerShape(3.dp))
            .clickable { onCheckedChange(!checked) }
    )
}

@Composable
fun BottomNavigationBar() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Card kuning kecil dengan rounded corner
        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            color = Color(0xFFFF9800),
            shape = RoundedCornerShape(25.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home icon dengan grid pattern minimalis
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(Color.White, RoundedCornerShape(1.dp))
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(1.5.dp)
                    ) {
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(Color.White, RoundedCornerShape(1.dp))
                            )
                        }
                    }
                }

                // Center scan icon
                Icon(
                    Icons.Outlined.CropFree,
                    contentDescription = "Scan",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )

                // Profile icon
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
