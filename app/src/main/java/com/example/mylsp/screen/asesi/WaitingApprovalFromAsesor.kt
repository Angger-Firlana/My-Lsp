package com.example.mylsp.screen.asesi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.util.AppFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WaitingAK01Screen(
    modifier: Modifier = Modifier,
    onNextStep: () -> Unit,
    onBackToForm: () -> Unit,
    onBackToDashboard: () -> Unit,
    formId: Int = 1,
    asesiName: String = "John Doe",
    initialStatus: String = "pending"
) {
    var currentStatus by remember { mutableStateOf(initialStatus) }
    var isRefreshing by remember { mutableStateOf(false) }
    var lastUpdated by remember { mutableStateOf<String?>(null) }
    var refreshCount by remember { mutableStateOf(0) }

    // Auto approve setelah 2x refresh untuk demo
    val handleRefresh = {
        isRefreshing = true

        // Simulate network call
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
            delay(2000) // 2 detik loading

            refreshCount++
            when {
                refreshCount >= 2 -> {
                    currentStatus = "accepted"
                }
                refreshCount == 1 -> {
                    currentStatus = "pending" // tetap pending di refresh pertama
                }
            }

            lastUpdated = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
            isRefreshing = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Icon dengan status
            StatusIcon(
                status = currentStatus,
                isLoading = isRefreshing
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = when (currentStatus) {
                    "pending" -> "Menunggu Persetujuan Asesor"
                    "accepted" -> "Asesmen AK01 Disetujui!"
                    "rejected" -> "Asesmen AK01 Ditolak"
                    else -> "Status Asesmen AK01"
                },
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "FR.AK.01 - PERSETUJUAN ASESMEN DAN KERAHASIAAN",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Status Card
            StatusCard(
                status = currentStatus,
                asesiName = asesiName,
                formId = formId,
                lastUpdated = lastUpdated,
                refreshCount = refreshCount
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Refresh Button (hanya muncul saat pending)
            if (currentStatus == "pending") {
                Button(
                    onClick ={ handleRefresh()},
                    enabled = !isRefreshing,
                    modifier = Modifier.width(220.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    if (isRefreshing) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Memeriksa Status...")
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Refresh",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (refreshCount == 0) "Periksa Status Asesor" else "Periksa Lagi",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Action Buttons
            ActionButtons(
                status = currentStatus,
                onNextStep = onNextStep,
                onBackToForm = onBackToForm,
                onBackToDashboard = onBackToDashboard
            )

            // Demo Info (hanya untuk development)
            if (currentStatus == "pending" && refreshCount < 2) {
                Spacer(modifier = Modifier.height(24.dp))
                DemoInfoCard(refreshCount = refreshCount)
            }
        }
    }
}

@Composable
private fun StatusIcon(
    status: String,
    isLoading: Boolean
) {
    val (icon, color) = when (status) {
        "pending" -> Icons.Filled.Pending to Color(0xFFFF9800)
        "accepted" -> Icons.Filled.CheckCircle to Color(0xFF4CAF50)
        "rejected" -> Icons.Filled.Assignment to Color(0xFFE53935)
        else -> Icons.Filled.Schedule to Color.Gray
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                color = color,
                strokeWidth = 4.dp
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = "Status",
                modifier = Modifier.size(60.dp),
                tint = color
            )
        }
    }
}

@Composable
private fun StatusCard(
    status: String,
    asesiName: String,
    formId: Int,
    lastUpdated: String?,
    refreshCount: Int
) {
    val (backgroundColor, textColor, statusText) = when (status) {
        "pending" -> Triple(
            Color(0xFFFFF3CD),
            Color(0xFFFF9800),
            "Sedang Menunggu Persetujuan"
        )
        "accepted" -> Triple(
            Color(0xFFE8F5E8),
            Color(0xFF4CAF50),
            "Disetujui - Siap Lanjut Asesmen"
        )
        "rejected" -> Triple(
            Color(0xFFFFEBEE),
            Color(0xFFE53935),
            "Ditolak - Perlu Perbaikan"
        )
        else -> Triple(
            Color(0xFFF5F5F5),
            Color.Gray,
            "Status Tidak Dikenal"
        )
    }

    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Status Asesmen",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = statusText,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = textColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Info Detail
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                InfoRow("Asesi", asesiName)
                InfoRow("Form ID", "AK01-$formId")
                InfoRow("Jenis", "Persetujuan Asesmen")

                if (lastUpdated != null) {
                    InfoRow("Terakhir Dicek", lastUpdated)
                }

                if (status == "pending" && refreshCount > 0) {
                    InfoRow("Pengecekan ke-", refreshCount.toString())
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .width(280.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ActionButtons(
    status: String,
    onNextStep: () -> Unit,
    onBackToForm: () -> Unit,
    onBackToDashboard: () -> Unit
) {
    when (status) {
        "accepted" -> {
            Button(
                onClick = onNextStep,
                modifier = Modifier.width(220.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Lanjut ke Asesmen Selanjutnya",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        "rejected" -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onBackToForm,
                    modifier = Modifier.width(220.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Isi Ulang Form AK01",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onBackToDashboard,
                    modifier = Modifier.width(220.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "Kembali ke Dashboard",
                        fontFamily = AppFont.Poppins
                    )
                }
            }
        }

        "pending" -> {
            Button(
                onClick = onBackToDashboard,
                modifier = Modifier.width(220.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = "Kembali ke Dashboard",
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

@Composable
private fun DemoInfoCard(refreshCount: Int) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎬 Demo Mode",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (refreshCount == 0) {
                    "Klik 'Periksa Status Asesor' 2x untuk approval otomatis"
                } else {
                    "Klik 'Periksa Lagi' ${2 - refreshCount}x lagi untuk approval"
                },
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}