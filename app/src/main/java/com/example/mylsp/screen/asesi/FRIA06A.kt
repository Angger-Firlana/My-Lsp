package com.example.mylsp.screen.asesi

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.util.AppFont
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FRIA06A(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF0F2F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Menggunakan HeaderForm yang sudah ada
                    HeaderForm(
                        title = "FR.IA.06A",
                        subTitle = "DPT - DAFTAR PERTANYAAN TERTULIS ESAI"
                    )
                    SchemaSection()
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Jawablah semua pertanyaan di bawah ini:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    QuestionsList()
                    Spacer(modifier = Modifier.height(24.dp))
                    SubmitButton()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchemaSection() {
    Column {
        Text(
            text = "Skema Serifikasi",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        val skemaDetails = mapOf(
            "Judul Unit" to "Okupasi Junior Custom Made",
            "Kode Unit" to "SKM.TBS.0JCM/LSP.SMKN24/2023",
            "TUK" to "Sewaktu/Tempat Kerja/Mandiri",
            "Nama Asesor" to ":",
            "Nama Asesi" to ":",
            "Tanggal" to LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            "Waktu" to ":"
        )
        skemaDetails.forEach { (label, value) ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    modifier = Modifier.width(150.dp),
                    fontSize = 14.sp
                )
                Text(text = ": ", fontSize = 14.sp)
                Text(text = value, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun QuestionsList() {
    val questions = getQuestions()
    questions.forEachIndexed { index, question ->
        QuestionCard(questionNumber = index + 1, questionText = question)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(questionNumber: Int, questionText: String) {
    var jawaban by remember { mutableStateOf("") }
    var pencapaian by remember { mutableStateOf<Boolean?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "$questionNumber. $questionText",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = jawaban,
                onValueChange = { jawaban = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = { Text("Tuliskan jawaban disini") },
                singleLine = false,
                // PERBAIKAN DI SINI
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pencapaian:", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = pencapaian == true,
                        onCheckedChange = { isChecked ->
                            pencapaian = if (isChecked) true else null
                        }
                    )
                    Text(text = "Ya", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = pencapaian == false,
                        onCheckedChange = { isChecked ->
                            pencapaian = if (isChecked) false else null
                        }
                    )
                    Text(text = "Tidak", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SubmitButton() {
    Button(
        onClick = { /* TODO: Aksi submit */ },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
    ) {
        Text(
            text = "Kirim Jawaban",
            fontSize = 16.sp
        )
    }
}

private fun getQuestions(): List<String> {
    return listOf(
        "Bagaimana cara Anda jika menghadapi pelanggan yang mengeluh karena adanya produk yang tidak sesuai dengan pesanannya?",
        "Perbedaan pendapat yang terjadi di lingkungan tempat kerja bisa menjadi tantangan tersendiri jika dihadapi dengan baik. Sebab, bisa menurunkan konflik yang berdampak negatif pada produktivitas dan kesejahteraan anggota tim. Bagaimana mengatasi perbedaan pendapat di tempat kerja dengan cara yang profesional?",
        "Apakah tujuan diadakannya prosedur keamanan, kesehatan dan keselamatan kerja?",
        "Sehabis menggunting mesin jahit bahan jahitan, sebaiknya membersihkan sisa benang pada mesin. Jika kita melakukan membersihkan sisa kain, benang, atau debu halus yang menempel pada mesin. Tuliskan peralatan yang digunakan untuk membersihkan mesin jahit!",
        "Mengukur tubuh merupakan langkah pertama pada kegiatan membuat suatu busana. Tuliskan salah satu fungsi mengukur tubuh!",
        "Shanin menjahit blus menggunakan mesin jahit engkol dengan tangan dan Yurike menjahit rok menggunakan mesin jahit dengan injakan pada kaki. Tuliskan penggolongan mesin jahit yang digunakan oleh Shanin dan Yurike!",
        "Di balik sejumlah kelebihan yang dimilikinya, teknik menjahit dengan tangan sebenarnya juga tidak luput dari kekurangan. Apa kekurangan menjahit dengan tangan bila dibandingkan dengan mesin jahit?",
        "Tuliskan teknik pengepresan pelapis tricot/kain gula pada bahan!",
        "Finishing adalah kegiatan penyelesaian akhir yang meliputi pemeriksaan (inspection), pembersihan (trimming), penyetrikaan (pressing) serta melipat dan mengemas. Tuliskan tujuan finishing pada busana!",
        "Desain hiasan busana merupakan suatu rancangan gambar yang nantinya akan diwujudkan dengan tujuan untuk memperindah suatu penampilan busana dengan menerapkan teknik sulaman. Hal apa saja yang diperhatikan dalam membuat hiasan busana?",
        "Standar mutu adalah suatu kondisi yang disepakati secara bersama-sama antara produsen dan pelanggannya (buyers). Apa tujuan melakukan pemeriksaan/ pengendalian mutu di industri busana?"
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun FRIA06APreview() {
    FRIA06A(navController = rememberNavController())
}