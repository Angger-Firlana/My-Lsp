package com.example.mylsp.screen.asesor.event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.util.AppFont

data class EventItem(
    val title: String,
    val date: String,
    val time: String,
    val participants: String,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun Events(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val events = listOf(
        EventItem(
            title = "USK RPL - Pemrograman Dasar",
            date = "Selasa, 8 Nov 2025",
            time = "07.00 - 15.00(Lab 1)",
            participants = "5 peserta",
            icon = Icons.Default.Code,
            iconColor = Color(0xFF2196F3)
        ),
        EventItem(
            title = "USK Kuliner - Pastry",
            date = "Selasa, 8 Nov 2025",
            time = "07.00 - 15.00(Dapur 3)",
            participants = "12 peserta",
            icon = Icons.Default.Restaurant,
            iconColor = Color(0xFF4CAF50)
        ),
        EventItem(
            title = "USK Hotel - Front office",
            date = "Selasa, 8 Nov 2025",
            time = "07.00 - 15.00(Hotel 24)",
            participants = "8 peserta",
            icon = Icons.Default.Hotel,
            iconColor = Color(0xFFFF9800)
        ),
        EventItem(
            title = "USK ULW - Ticketing",
            date = "Selasa, 8 Nov 2025",
            time = "07.00 - 15.00(Anthorium)",
            participants = "6 peserta",
            icon = Icons.Default.ConfirmationNumber,
            iconColor = Color(0xFF9C27B0)
        ),
        EventItem(
            title = "USK Busana - Menjahit dasar",
            date = "Selasa, 8 Nov 2025",
            time = "07.00 - 15.00(Sanggar)",
            participants = "15 peserta",
            icon = Icons.Default.ContentCut,
            iconColor = Color(0xFFE91E63)
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Pilih Event Yang Terjadwal",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onClick = {
                        navController.navigate("detail_event")
                    }
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: EventItem,
    onClick: ()-> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable{ onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = event.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = event.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = event.date,
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = event.time,
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = event.participants,
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}