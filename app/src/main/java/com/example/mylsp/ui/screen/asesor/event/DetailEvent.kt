package com.example.mylsp.ui.screen.asesor.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R
import com.example.mylsp.model.api.Apl01
import com.example.mylsp.data.api.asesi.AssesmentAsesi
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.user.AsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Participant(
    val name: String,
    val nis: String,
    val startedAt: String,
    val endAt: String,
    val part: String,
    val status: ParticipantStatus,
    val progress: Float
)

enum class ParticipantStatus {
    COMPLETED,
    ACTIVE,
    PENDING
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailEvent(
    modifier: Modifier = Modifier,
    userManager: UserManager,
    idAssesment: Int,
    assessmentViewModel: AssesmentViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    onDetailAssessi: (Int, Apl01) -> Unit
) {
    val gradientColors = listOf(
        Color(0xFFFE9C54), // Orange terang (FE9C54 dari color stops)
        Color(0xFFFFFFFF), // Orange medium
    )
    val listAssessment by assessmentViewModel.listAssessment.collectAsState()
    val listAssesmentAsesi by assesmentAsesiViewModel.listAssesmentAsesi.collectAsState()

    // State untuk pull refresh
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Function untuk refresh data
    val onRefresh: () -> Unit = {
        scope.launch {
            isRefreshing = true
            // Refresh data dari API
            assessmentViewModel.getListAssesment()
            assesmentAsesiViewModel.getListAsesiByAssesment(idAssesment)
            // Simulasi delay untuk menunjukkan loading indicator
            delay(1000)
            isRefreshing = false
        }
    }

    // Pull refresh state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    LaunchedEffect(Unit) {
        assessmentViewModel.getListAssesment()
        assesmentAsesiViewModel.getListAsesiByAssesment(idAssesment)
    }

    if (listAssessment.isNotEmpty()){
        val assesment = listAssessment.find { it.id == idAssesment }
        assesment?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors
                        )
                    )
                    .pullRefresh(pullRefreshState) // Tambahkan pull refresh modifier
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderSection(
                        title = userManager.getUserName()?: "",
                        subTitle = "K3 RPL"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        assesment.schema.judul_skema,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.background,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    StatsSection(
                        totalPeserta = listAssesmentAsesi.size,
                        selesai = listAssesmentAsesi.count {
                            // Sesuaikan kondisi dengan data real Anda
                            false // Placeholder - ganti dengan kondisi sebenarnya
                        },
                        sedangUjian = listAssesmentAsesi.count {
                            // Sesuaikan kondisi dengan data real Anda
                            true // Placeholder - ganti dengan kondisi sebenarnya
                        },
                        belumMulai = listAssesmentAsesi.count {
                            // Sesuaikan kondisi dengan data real Anda
                            false // Placeholder - ganti dengan kondisi sebenarnya
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .background(color = Color.Gray)
                            .width(400.dp)
                            .height(3.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            "Asesi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFont.Poppins,
                            color = Color(0xFF085A70)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ParticipantsList(
                        participants = listAssesmentAsesi,
                        onClick = { id, asesi -> onDetailAssessi(id, asesi)}
                    )
                }

                // Pull refresh indicator
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = Color.White,
                    contentColor = Color(0xFFFE9C54)
                )
            }
        }
    }
}

@Composable
fun HeaderSection(title: String, subTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageBitmap = ImageBitmap.imageResource(
            LocalContext.current.resources,
            R.drawable.senaaska
        )

        Image(
            painter = BitmapPainter(imageBitmap),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(4.dp, Color.Transparent, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = AppFont.Poppins
            )

            Text(
                text = subTitle,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                fontFamily = AppFont.Poppins
            )
        }
    }
}

@Composable
fun StatsSection(
    totalPeserta: Int = 26,
    selesai: Int = 10,
    sedangUjian: Int = 5,
    belumMulai: Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatCard(
            number = totalPeserta.toString(),
            label = "Total Peserta",
            icon = Icons.Default.PeopleOutline,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = selesai.toString(),
            label = "Selesai",
            icon = Icons.Default.CheckCircleOutline,
            color = Color.Green,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = sedangUjian.toString(),
            label = "Sedang Ujian",
            icon = Icons.Default.DonutLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = belumMulai.toString(),
            label = "Belum Mulai",
            icon = Icons.Default.Cancel,
            color = Color.Red,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    number: String,
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray,
                fontFamily = AppFont.Poppins
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = number,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

@Composable
fun ParticipantsList(participants: List<AssesmentAsesi>, onClick: (Int, Apl01) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(participants) { participant ->
            ParticipantCard(
                participant = participant,
                onClick = {
                    onClick(participant.id, participant.asesi)
                }
            )
        }
    }
}

@Composable
fun ParticipantCard(participant: AssesmentAsesi, onClick: (Int) -> Unit) {
    val context = LocalContext.current
    val assesmentAsesiManager = AssesmentAsesiManager(context = context)
    val asesiManager = AsesiManager(context)
    Card(
        onClick = {
            asesiManager.setId(participant.assesi_id)
            assesmentAsesiManager.setAssesmentAsesiId(participant.id)
            assesmentAsesiManager.saveAssesmentAsesi(participant)
            onClick(participant.id)
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Nama + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = participant.asesi.nama_lengkap?: "Nama Asesi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = AppFont.Poppins
                    )
                    Text(
                        text = "NIK: ${participant.asesi.no_ktp}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = AppFont.Poppins
                    )
                }

                // Status pill
                Box(
                    modifier = Modifier
                        .background(
                            color = getStatusColor(participant),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = getStatusText(participant),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = AppFont.Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status: ",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    text = participant.status ?: "Belum Diketahui",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = getStatusTextColor(participant),
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

// Helper functions
fun getStatusColor(participant: AssesmentAsesi): Color {
    // Sesuaikan dengan status dari AssesmentAsesi
    return when (participant.status?.lowercase()) {
        "selesai", "completed", "lulus", "kompeten" -> Color.Green
        "sedang ujian", "apl-02", "ak-01","ak-02", "ak-03", "ak-04","ak-05",-> Color(0xFFFFA500) // Orange
        "belum", "pending", "menunggu" -> Color.Gray
        "gagal", "failed", "tidak lulus", "belum kompeten" -> Color.Red
        else -> Color.Gray
    }
}

fun getStatusText(participant: AssesmentAsesi): String {
    // Menggunakan status dari AssesmentAsesi
    return participant.status ?: "Status Tidak Diketahui"
}

fun getStatusTextColor(participant: AssesmentAsesi): Color {
    // Warna teks untuk status di bagian bawah card
    return when (participant.status?.lowercase()) {
        "selesai", "completed", "lulus" -> Color(0xFF4CAF50)
        "sedang ujian", "in progress", "proses" -> Color(0xFFFFA500)
        "belum mulai", "pending", "menunggu" -> Color.Gray
        "gagal", "failed", "tidak lulus" -> Color(0xFFF44336)
        else -> Color.Black
    }
}