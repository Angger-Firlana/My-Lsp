package com.example.mylsp.screen.asesor

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont

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

@Composable
fun DetailEvent(
    modifier: Modifier = Modifier,
    idAssesment: Int,
    onDetailAssessi: (Int) -> Unit
) {
    val participants = listOf(
        Participant(
            "Ashlan Usfar Rahmi", "12345", "08.07", "",
            "FR APL 02", ParticipantStatus.PENDING, 0.4f
        ),
        Participant(
            "Anggita Priscilla", "12346", "08.07", "12.01",
            "FR APL 03",ParticipantStatus.COMPLETED, 0.9f
        ),
        Participant(
            "Arya Satya Armand Rai", "12347", "08.07", "",
            "FR APL 01", ParticipantStatus.ACTIVE, 0.0f
        ),
        Participant(
            "Azka Shafila Akbad", "12348", "08.07", "",
            "FR APL 02", ParticipantStatus.PENDING, 0.6f
        ),
        Participant(
            "Raffi Khairulna", "12349", "08.07", "",
            "FR APL 01", ParticipantStatus.PENDING, 0.3f
        ),
        Participant(
            "Muhammad Rizkianussh", "12350", "08.07", "",
            "FR APL 02", ParticipantStatus.ACTIVE, 0.7f
        )
    )

    val gradientColors = listOf(
        Color(0xFFFE9C54), // Orange terang (FE9C54 dari color stops)
        Color(0xFFFFFFFF), // Orange medium
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(
                title = "Prof. Arul Maulido Singo, M.kom.",
                subTitle = "K3 RPL"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "USK RPL - Pemrograman Dasar",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(20.dp))

            StatsSection()

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

            ParticipantsList(participants = participants, onClick = {onDetailAssessi(it)})
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
fun StatsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatCard(
            number = "26", label = "Total Peserta", icon = Icons.Default.PeopleOutline,
            color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = "10", label = "Selesai", icon = Icons.Default.CheckCircleOutline,
            color = Color.Green, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = "5", label = "Sedang Ujian", icon = Icons.Default.DonutLarge,
            color = MaterialTheme.colorScheme.secondary, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            number = "0", label = "Belum Mulai", icon = Icons.Default.Cancel,
            color = Color.Red, modifier = Modifier.weight(1f)
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
fun ParticipantsList(participants: List<Participant>, onClick: (Int) -> Unit) {
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
                   onClick(1)
                }
            )

        }
    }
}

@Composable
fun ParticipantCard(participant: Participant, onClick: () -> Unit) {
    val (statusText, statusColor) = when (participant.status) {
        ParticipantStatus.COMPLETED -> "Selesai" to Color(0xFF4CAF50) // Hijau
        ParticipantStatus.ACTIVE -> "Sedang Ujian" to Color(0xFF2196F3) // Biru
        ParticipantStatus.PENDING -> "Belum Mulai" to Color(0xFF707070) // Abu-abu
    }

    Card(
        onClick = {
            onClick()
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
                        text = participant.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = AppFont.Poppins
                    )
                    Text(
                        text = "NIS: ${participant.nis}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = AppFont.Poppins
                    )
                }

                // Status pill
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = statusText,
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = AppFont.Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Waktu mulai", fontSize = 10.sp, color = Color.Gray, fontFamily = AppFont.Poppins)
                    Text(participant.startedAt, fontSize = 12.sp, color = Color.Black, fontFamily = AppFont.Poppins)
                }
                Column {
                    Text("Waktu selesai", fontSize = 10.sp, color = Color.Gray, fontFamily = AppFont.Poppins)
                    Text(participant.endAt, fontSize = 12.sp, color = Color.Black, fontFamily = AppFont.Poppins)
                }
                Column {
                    Text("Bagian saat ini", fontSize = 10.sp, color = Color.Gray, fontFamily = AppFont.Poppins)
                    Text(participant.part, fontSize = 12.sp, color = Color.Black, fontFamily = AppFont.Poppins)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar + persen
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { participant.progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(50)),
                    color = Color(0xFF85B6F7),
                    trackColor = Color(0xFFE0E0E0),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${(participant.progress * 100).toInt()}%",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}