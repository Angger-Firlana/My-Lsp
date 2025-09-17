package com.example.mylsp.screen.asesor.ia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

@Composable
fun FRIA02(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderForm(
            "FR.IA.02.TPD",
            "TUGAS PRAKTIK DEMONSTRASI"
        )

        SkemaSertifikasi(
            judulUnit = "Junior Custom Mode",
            kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
            TUK = "Sewaktu/Tempat Kerja/Mandiri"
        )

        Petunjuk()

        Skenario()

        Conditions(
            equipments = listOf("APD", "Peralatan Menjahit"),
            duration = 30
        )

        KirimButton(
            onClick = { }
        )
    }
}

@Composable
fun Petunjuk(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "A. Petunjuk",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(8.dp))

        val listPetunjuk = listOf(
            "1. Baca dan pelajari setiap instruksi kerja di bawah ini dengan cermat sebelum melaksanakan praktek.",
            "2. Klarifikasi kepada asesor kompentensi apabila ada hal-hal yang belum jelas.",
            "3. Laksanakan pekerjaan sesuai dengan urutan proses yang sudah di tetapkan.",
            "4. Seluruh proses kerja mengacu kepada SOP/WI yang dipersyaratkan."
        )

        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFF7A00).copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            listPetunjuk.forEach { petunjuk ->
                Text(
                    petunjuk,
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun Skenario(modifier: Modifier = Modifier, ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            "B. Skenario Tugas Praktik Demonstrasi",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            "Kelompok Pekerjaan 1",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            modifier = modifier.padding(top = 8.dp, start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val unitData = listOf(
                    Pair("GAR.CM01.001.01", "Memberikan Layanan Secara Prima kepada Pelanggan"),
                    Pair("GAR.CM01.002.01", "Melakukan Pekerjaan dalam Lingkungan Sosial yang Beragam"),
                    Pair("GAR.CM01.003.01", "Mengikuti Prosedur Kesehatan, Keselamatan dan Keamanan dalam Bekerja"),
                    Pair("GAR.CM01.004.01", "Memelihara Alat Jahit")
                )

                unitData.forEachIndexed { index, (kode, judul) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${index + 1}.",
                            fontSize = 14.sp,
                            fontFamily = AppFont.Poppins,
                            color = Color.Black,
                            modifier = Modifier.width(20.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Kode Unit: $kode",
                                fontSize = 14.sp,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Judul Unit:",
                                fontSize = 13.sp,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = judul,
                                fontSize = 12.sp,
                                fontFamily = AppFont.Poppins,
                                color = Color.Black,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    if (index < unitData.size - 1) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.3f),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            "Skenario Tugas Praktik Demonstrasi",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            "Anda adalah seorang Operator jahit dan diminta untuk melayani yang ingin membuat bius sesuai dengan sample yang ada.",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Conditions(
    modifier: Modifier = Modifier,
    equipments: List<String?>,
    duration: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 32.dp)
    ) {
        Text(
            "Perlengkapan dan Peralatan: ",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        equipments.forEach { equipment ->
            equipment?.let {
                Column(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text = "• $it",
                        fontFamily = AppFont.Poppins,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(16.dp))

        Row(modifier = modifier.fillMaxWidth()) {
            Text(
                "Durasi Waktu: ",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = duration.toString() + "Menit",
                fontFamily = AppFont.Poppins,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun KirimButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                "Kirim Jawaban",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )
        }
    }
}