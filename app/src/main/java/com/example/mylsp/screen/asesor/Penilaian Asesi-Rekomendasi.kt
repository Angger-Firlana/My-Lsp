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
                        fontSize = 18.sp,
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Nama Mata Uji Kompetensi
            Text(
                text = "Nama Mata Uji Kompetensi: USK PRL - Pemrograman Dasar",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
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
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = "Lengkap",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            // Data Diri with button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Data Diri",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = "Lengkap",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            // Divider line
            Divider(
                color = Color.Gray.copy(alpha = 0.1f),
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Program info
            Text(
                text = "Program: Rekayasa Perangkat Lunak",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Kelas info
            Text(
                text = "Kelas: 11",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // NISN info
            Text(
                text = "NISN: 076231233",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Email info
            Text(
                text = "email: afdhal322@gmail.com",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // Rekomendasi title
            Text(
                text = "Rekomendasi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2196F3),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            // Checkbox options
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kompeten",
                        fontSize = 14.sp,
                        color = Color.Black
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Belum Kompeten",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Kirim button - positioned at bottom
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Kirim",
                        color = Color.White,
                        fontSize = 12.sp,
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
            .size(20.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .background(if (checked) Color(0xFF2196F3) else Color.Transparent)
            .clickable { onCheckedChange(!checked) }
    )
}

@Composable
fun BottomNavigationBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = Color(0xFFFF9800),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home icon dengan grid pattern
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color.White, RoundedCornerShape(1.dp))
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color.White, RoundedCornerShape(1.dp))
                            )
                        }
                    }
                }
            }

            // Center scan icon
            Icon(
                Icons.Outlined.CropFree,
                contentDescription = "Scan",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )

            // Profile icon
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PenilaianAsesiRekomendasiScreenPreview() {
    PenilaianAsesiRekomendasiScreen()
}
