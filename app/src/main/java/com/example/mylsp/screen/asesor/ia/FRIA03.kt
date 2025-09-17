package com.example.mylsp.screen.asesor.ia

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

@Composable
fun FRIA03(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderForm(
            "FR.IA.03",
            "Pertanyaan Untuk Mendukung Observasi"
        )

        SkemaSertifikasi(
            judulUnit = "Junior Custom Mode",
            kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
            TUK = "Sewaktu/Tempat Kerja/Mandiri"
        )

        Paduan()

        KelompokPekerjaan(
            listWork = listOf(
                "GAR.CM01.003.01 Mengikuti Prosedur Kesehatan",
                "dst",
                "dst",
                "dst"
            )
        )

        Pertanyaan(
            listPertanyaan = listOf(
                "Anda seorang operator yunior busana, sebelum memulai kegiatan menjahit blus, anda perlu memperhatikan SOP kesehatan dan keselamatan kerja, apa yang akan anda lakukan supaya tidak terjadi kecelakaan kerja pada waktu menjahit blus? (JRES)"
            )
        )

        Spacer(modifier = modifier.height(8.dp))

        SubmitButton(
            onClick = {  }
        )
    }
}

@Composable
fun Paduan(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        val listPetunjuk = listOf(
            "Formulir ini di isi oleh asesor kompetensi dapat sebelum, pada saat atau setelah melakukan asesmen dengan metode observasi demonstrasi.",
            "Pertanyaan dibuat dengan tujuan untuk menggali, dapat berisi pertanyaan yang berkaitan dengan dimensi kompetensi, batasan variabel dan aspek kritis yang relevan dengan skenario tugas dan praktik demonstrasi.",
            "Jika pertanyaan disampaikan sebelum asesi melakukan praktik demonstrasi, maka pertanyaan dibuat berkaitan dengan aspek K3L, SOP, penggunaan peralatan dan perlengkapan.",
            "Jika setelah asesi melakukan praktik demonstrasi terdapat item pertanyaan pendukung observasi telah terpenuhi, maka pertanyaan tersebut tidak perlu ditanyakan lagi dan cukup memberi catatan bahwa sudah terpenuhi pada saat tugas praktek demonstrasi pada kolom tanggapan.",
            "Jika pada saat observasi ada hal yang perlu dikonfirmasi sedangkan di instrumen daftar pertanyaan pendukung observasi tidak ada, maka asesor dapat memberikan pertanyaan dengan syarat pertanyaan harus berkaitan dengan tugas praktek demonstrasi. Jika dilakukan, asesor harus mencatat dalam instrumen pertanyaan pendukung observasi.",
            "Tanggapan asesi ditulis kolom tanggapan"
        )

        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFF7A00).copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    "Paduan Bagi Asesor",
                    fontSize = 16.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Instruksi: ",
                    fontSize = 16.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.SemiBold
                )

                listPetunjuk.forEach { petunjuk ->
                    Text(
                        text = "• $petunjuk",
                        fontFamily = AppFont.Poppins,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KelompokPekerjaan(
    modifier: Modifier = Modifier,
    listWork: List<String?>
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "Kelompok Pekerjaan 1",
            fontSize = 16.sp,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(8.dp))

        listWork.forEachIndexed { index, work ->
            work?.let {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Row(modifier = modifier.fillMaxWidth()) {
                        Text(
                            text = "${index + 1}.",
                            fontSize = 14.sp,
                            fontFamily = AppFont.Poppins,
                            color = Color.Black,
                            modifier = Modifier.width(20.dp)
                        )

                        Text(
                            text = it,
                            fontFamily = AppFont.Poppins,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Pertanyaan(
    modifier: Modifier = Modifier,
    listPertanyaan: List<String?>
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Pertanyaan",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = modifier.height(16.dp))

            Row(modifier = modifier.fillMaxWidth()) {
                listPertanyaan.forEachIndexed { index, pertanyaan ->
                    pertanyaan?.let {
                        Text(
                            text = "${index + 1}. ",
                            fontSize = 12.sp,
                            fontFamily = AppFont.Poppins
                        )

                        Text(
                            text = pertanyaan,
                            fontSize = 12.sp,
                            fontFamily = AppFont.Poppins
                        )
                    }
                }
            }

            Spacer(modifier = modifier.height(8.dp))

            Text(
                "Pencapaian",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                modifier = modifier.padding(start = 16.dp)
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = { }
                )
                Text(
                    text = "Ya",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium
                )

                Checkbox(
                    checked = false,
                    onCheckedChange = { }
                )
                Text(
                    text = "Tidak",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = modifier.height(8.dp))

            Text(
                "Tanggapan",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                value = "penggunaan alat pelindung diri (APD) seperti bidal atau pelindung ",
                onValueChange = {  },
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                ),
                readOnly = true,
                maxLines = 4,
                singleLine = false,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = { }
                )

                Text(
                    text = "R",
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
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