package com.example.mylsp.screen.asesi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.model.api.Assessment
import com.example.mylsp.util.AssessmentManager
import com.example.mylsp.util.UserManager
import com.example.mylsp.viewmodel.APL01ViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel

@Composable
fun DetailAssesment(
    modifier: Modifier = Modifier,
    userManager: UserManager,
    onClickKerjakan: (Assessment) -> Unit,
    apL01ViewModel: APL01ViewModel,
    assessmentViewModel: AssesmentViewModel,
    idAssessment: Int
) {

    val listAssessment by assessmentViewModel.listAssessment.collectAsState()
    val asesi by apL01ViewModel.formData.collectAsState()

    LaunchedEffect(Unit) {
        assessmentViewModel.getListAssesment()
        apL01ViewModel.fetchFormApl01Status()
    }

    if (listAssessment.isNotEmpty()) {
        val assessment = listAssessment.find { it.id == idAssessment }
        assessment?.let { data ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFF4DD0E1))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header user info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = asesi?.nama_lengkap ?: "Nama Asesi",
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                            Text(
                                text = asesi?.no_ktp ?: "NIK",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Judul skema
                    Text(
                        text = data.schema.judul_skema,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Card detail assessment
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = data.schema.judul_skema,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Tanggal assessment
                            InfoItem(
                                icon = Icons.Default.CalendarToday,
                                text = data.tanggal_assesment
                            )

                            // Jam + tempat (sementara jam belum ada di API, aku combine aja)
                            InfoItem(
                                icon = Icons.Default.Schedule,
                                text = "Jam belum ada • ${data.tuk}"
                            )

                            // Nama jurusan / kompetensi (kalau ada mapping bisa pakai)
                            InfoItem(
                                icon = Icons.Default.Computer,
                                text = data.schema.judul_skema
                            )

                            // Nomor skema
                            InfoItem(
                                icon = Icons.Default.Tag,
                                text = data.schema.nomor_skema
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Panduan
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Panduan Asesmen Mandiri",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "• Baca setiap pertanyaan di kolom sebelah kiri",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        lineHeight = 20.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )

                                    Text(
                                        text = "• Beri tanda centang (✓) pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        lineHeight = 20.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )

                                    Text(
                                        text = "• Isi kolom di sebelah kanan dengan bukti yang Anda miliki.",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {
                                    onClickKerjakan(data)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4DD0E1)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Kerjakan",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
