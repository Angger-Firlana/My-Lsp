package com.example.mylsp.screen.asesor.ia

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
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(vertical = 16.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "APDHAI LEZHAR RAHMA PANGESTU",
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
                .verticalScroll(scrollState)
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
                        text = "FR.IA.09",
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
                    label = "Judul",
                    value = "MENGSAMBANG PERSIAPAN DAN PEMELIHARAAN RESERVOIR PLAN DALAM MENJANGKAU POTENSI KAPASITAS"
                )
                IA09FormRow(
                    label = "Nomor",
                    value = "Semen/Template Kerja/Mandiri"
                )
                IA09FormRow(
                    label = "TUK",
                    value = ""
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

            Spacer(modifier = Modifier.height(20.dp))

            // Unit Kompetensi Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Unit Kompetensi",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                IA09FormRow(label = "Kode Unit", value = "LSP.NTC.090.003")
                IA09FormRow(label = "Judul Unit", value = "Memproses Reservoir")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Setiap pertanyaan section
            Text(
                text = "Setiap pertanyaan harus terkait dengan elemen",
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Tuliskan bukti-bukti yang berkaitan dengan KUK, batasi jumlah pertanyaan, setiap pertanyaan harus menggambarkan satu atau lebih KUK yang terkait berdasarkan bukti yang dikumpulkan",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Bukti-Bukti Kompetensi Section
            Text(
                text = "Bukti-Bukti Kompetensi",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Satu box besar untuk bukti kompetensi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column {
                    Text("1.", fontSize = 12.sp, color = Color.Black)
                    Text("2.", fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(top = 4.dp))
                    Text("3.", fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(top = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Daftar Pertanyaan Wawancara dan Rekomendasi dalam satu box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Daftar Pertanyaan Wawancara
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Daftar Pertanyaan Wawancara",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        repeat(3) { index ->
                            Text(
                                text = "${index + 1}. Sesuai dengan bukti :",
                                fontSize = 10.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    // Rekomendasi
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Rekomendasi",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        repeat(3) { index ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .border(1.dp, Color.Gray)
                                )
                                Text(
                                    text = "K",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .border(1.dp, Color.Gray)
                                )
                                Text(
                                    text = "BK",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nama Asesi dan Nama Asesor (di luar box)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nama Asesi",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nama Asesor",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tanda Tangan Asesi (di luar box)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tanda Tangan Asesi",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
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