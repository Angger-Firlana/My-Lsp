package com.example.mylsp.screen.asesor

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FRIA06C(modifier: Modifier = Modifier, navController: NavController) {

    var answer1 by remember { mutableStateOf("") }
    var answer2 by remember { mutableStateOf("") }
    var answer3 by remember { mutableStateOf("") }
    var answer4 by remember { mutableStateOf("") }
    var answer5 by remember { mutableStateOf("") }

    var checked1 by remember { mutableStateOf<Boolean?>(null) }
    var checked2 by remember { mutableStateOf<Boolean?>(null) }
    var checked3 by remember { mutableStateOf<Boolean?>(null) }
    var checked4 by remember { mutableStateOf<Boolean?>(null) }
    var checked5 by remember { mutableStateOf<Boolean?>(null) }

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
                .background(Color.White)
                .padding(16.dp)
        ) {

            HeaderForm(
                "FR.IA.06.C",
                "LEMBAR JAWABAN PERTANYAAN TERTULIS ESSAI"
            )


        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            SkemaSertifikasi(
                judulUnit = "Okupasi Junior Custom Made",
                kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
                TUK = "Sewaktu/Tempat Kerja/Mandiri",
                namaAsesor = null,
                namaAsesi = null,
                tanggalAsesmen = null
            )

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red,
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Jawablah semua pertanyaan di bawah ini:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            QuestionCard(
                number = "1",
                question = "Bagaimana cara Anda jika menghadapi pelanggan yang mengeluh karena adanya produk yang tidak sesuai dengan pesanannya?",
                answer = answer1,
                onAnswerChange = { answer1 = it },
                checked = checked1,
                onCheckedChange = { checked1 = it }
            )

            QuestionCard(
                number = "2",
                question = "Perbedaan pendapat yang terjadi di lingkungan tempat kerja bisa menjadi tantangan tersendiri jika tidak dihadapi dengan baik. Sebab, bisa menurunkan konflik yang berdampak negatif pada produktivitas dan kesejahteraan anggota tim. Bagaimana mengatasi perbedaan pendapat di tempat kerja dengan cara yang profesional?",
                answer = answer2,
                onAnswerChange = { answer2 = it },
                checked = checked2,
                onCheckedChange = { checked2 = it }
            )

            QuestionCard(
                number = "3",
                question = "Apakah tujuan diadakannya prosedur keamanan kesehatan dan keselamatan kerja?",
                answer = answer3,
                onAnswerChange = { answer3 = it },
                checked = checked3,
                onCheckedChange = { checked3 = it }
            )

            QuestionCard(
                number = "4",
                question = "Sehabis menggunting mesin jahit bahan jahitan, sebaiknya membersihkan sisa benang pada mesin. Jika kita melakukan membersihkan sisa kain, benang, atau debu halus yang menempel pada mesin. Tuliskan peralatan yang digunakan untuk membersihkan mesin jahit!",
                answer = answer4,
                onAnswerChange = { answer4 = it },
                checked = checked4,
                onCheckedChange = { checked4 = it }
            )

            QuestionCard(
                number = "5",
                question = "Mengukur tubuh merupakan langkah pertama pada kegiatan membuat suatu busana. Tuliskan salah satu fungsi mengukur tubuh!",
                answer = answer5,
                onAnswerChange = { answer5 = it },
                checked = checked5,
                onCheckedChange = { checked5 = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .border(1.dp, Color(0xFF2F2F2F), RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("APPROVE",
                        color = Color(0xFF2F2F2F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .border(1.dp, Color(0xFF2F2F2F), RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("REJECT",
                        color = Color(0xFF2F2F2F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(
    number: String,
    question: String,
    answer: String,
    onAnswerChange: (String) -> Unit,
    checked: Boolean?,
    onCheckedChange: (Boolean?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "$number.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = question,
                fontSize = 13.sp,
                color = Color.Black,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = answer,
                onValueChange = onAnswerChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(4.dp),
                placeholder = {
                    Text("Tulis jawaban disini", fontSize = 12.sp, color = Color.Gray)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pencapaian:",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checked == true,
                            onCheckedChange = { onCheckedChange(true) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF4CAF50)
                            )
                        )
                        Text("Ya", modifier = Modifier.padding(start = 4.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checked == false,
                            onCheckedChange = { onCheckedChange(false) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF4CAF50)
                            )
                        )
                        Text("Tidak", modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }
        }
    }
}
    @Preview(
        showBackground = true,
        device = "spec:width=411dp,height=891dp",
        showSystemUi = true
    )
    @Composable
    fun PreviewFRIA() {
        MaterialTheme {
            Surface {
                FRIA06C(navController = rememberNavController())
            }
        }
    }