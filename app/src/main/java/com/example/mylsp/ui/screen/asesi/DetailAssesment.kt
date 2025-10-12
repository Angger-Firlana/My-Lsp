package com.example.mylsp.ui.screen.asesi

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mylsp.data.api.assesment.Assessment
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.viewmodel.assesment.apl.APL01ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel

@Composable
fun DetailAssesment(
    modifier: Modifier = Modifier,
    userManager: UserManager,
    onClickKerjakan: (Int) -> Unit,
    asesiViewModel: AsesiViewModel,
    apL01ViewModel: APL01ViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    assessmentViewModel: AssesmentViewModel,
    idAssessment: Int
) {
    val context = LocalContext.current
    val listAssessment by assessmentViewModel.listAssessment.collectAsState()
    val asesi by apL01ViewModel.formData.collectAsState()
    val assesi by asesiViewModel.asesi.collectAsState()
    val stateDaftar by assesmentAsesiViewModel.state.collectAsState()

    // State untuk dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(stateDaftar) {
        stateDaftar?.let { success ->
            if (success){
                showSuccessDialog = true
            } else {
                Toast.makeText(context, "Gagal daftar assesment", Toast.LENGTH_SHORT).show()
            }
            assesmentAsesiViewModel.clearState()
        }
    }

    LaunchedEffect(Unit) {
        assessmentViewModel.getListAssesment()
        asesiViewModel.getDataAsesiByUser(userManager.getUserId()?.toInt() ?: 0)
        apL01ViewModel.fetchFormApl01Status()
    }

    // Success Dialog
    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
            },
            onStartAssessment = {
                showSuccessDialog = false
                onClickKerjakan(idAssessment)
            },
            assessmentName = listAssessment.find { it.id == idAssessment }?.schema?.judul_skema ?: "Asesmen"
        )
    }

    if (listAssessment.isNotEmpty()) {
        val assessment = listAssessment.find { it.id == idAssessment }
        assessment?.let { data ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 72.dp, top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    // Header user info
                    UserProfileCard(
                        userName = assesi?.nama_lengkap ?: "Nama Asesi",
                        userNik = assesi?.no_ktp ?: "NIK"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Judul skema
                    Text(
                        text = data.schema.judul_skema,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFont.Poppins,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Card detail assessment
                    AssessmentDetailCard(
                        data = data,
                        onKerjakanClick = {
                            assesmentAsesiViewModel.daftarAssesment(
                                assesmentId = data.id,
                                assesiId = assesi?.id ?: 0
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessDialog(
    onDismiss: () -> Unit,
    onStartAssessment: () -> Unit,
    assessmentName: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✓",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Pendaftaran Berhasil!",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Anda telah berhasil mendaftar untuk asesmen:",
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = assessmentName,
                    fontFamily = AppFont.Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartAssessment,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "Mulai Asesmen",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Nanti Saja",
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun UserProfileCard(
    userName: String,
    userNik: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = userName,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    text = "NIK: $userNik",
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

@Composable
private fun AssessmentDetailCard(
    data: com.example.mylsp.data.api.assesment.Assessment,
    onKerjakanClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Header dengan icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Detail Asesmen",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Informasi lengkap skema sertifikasi",
                        fontSize = 14.sp,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Assessment info items
            InfoItem(
                icon = Icons.Default.Tag,
                label = "Nomor Skema",
                text = data.schema.nomor_skema
            )

            InfoItem(
                icon = Icons.Default.CalendarToday,
                label = "Tanggal Asesmen",
                text = data.tanggal_assesment
            )

            InfoItem(
                icon = Icons.Default.LocationOn,
                label = "Tempat Uji Kompetensi",
                text = data.tuk
            )

            InfoItem(
                icon = Icons.Default.Computer,
                label = "Skema Kompetensi",
                text = data.schema.judul_skema
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Panduan Section
            PanduanSection()

            Spacer(modifier = Modifier.height(32.dp))

            // Action Button
            Button(
                onClick = onKerjakanClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Daftar & Mulai Asesmen",
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    label: String,
    text: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun PanduanSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Panduan Asesmen Mandiri",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val panduanItems = listOf(
                "Baca setiap pertanyaan di kolom sebelah kiri dengan teliti",
                "Beri tanda centang (✓) pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan",
                "Isi kolom di sebelah kanan dengan bukti yang Anda miliki untuk menunjukkan kompetensi"
            )

            panduanItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFont.Poppins
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item,
                        fontSize = 14.sp,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (index < panduanItems.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}