package com.example.mylsp.screen.asesor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.Util
import com.example.mylsp.viewmodel.MukViewModel
import com.example.mylsp.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkemaListScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val userViewModel: UserViewModel = viewModel()
    val viewModel: MukViewModel = viewModel()
    val skemaList by viewModel.skemas.collectAsState()


    var search by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Semua Event") }

    val filterOptions = listOf("Semua Event", "Event Hari Ini", "Event Minggu Ini", "Event Bulan Ini")

    if (true){
        LaunchedEffect(Unit) {
            Log.d("User", "")
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Header Background
            if (true){
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(320.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                // Header content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Event Terjadwal",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pilih event yang ingin Anda ikuti",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Search Field
                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Cari event...",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        },
                        trailingIcon = {
                            Box {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(
                                        Icons.Default.FilterList,
                                        contentDescription = "Filter",
                                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    filterOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                selectedFilter = option
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Filter indicator
                    if (selectedFilter != "Semua Event") {
                        Text(
                            text = "Filter: $selectedFilter",
                            fontFamily = AppFont.Poppins,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Content

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        top = 240.dp,
                        bottom = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                }
            }else{
                WaitingApprovalScreen(modifier, navController)
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SkemaCard(
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Extract data from skema - adjust these according to your actual data structure
    val judulSkema = "" // skema.judulSkema
    val tanggalBerlaku = ""

    val dateSkema = LocalDate.parse(tanggalBerlaku)
    val tanggalSkema = dateSkema.format(
        DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale("id", "ID"))
    )

    val jamSkema = "09:00 - 12:00"
    val tempatKerja = "Lab Komputer"
    val jumlahPeserta = "72"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onCardClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Title
            Text(
                text = judulSkema,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Event Details
            EventDetailRow(
                icon = Icons.Default.DateRange,
                text = tanggalSkema,
                iconTint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            EventDetailRow(
                icon = Icons.Default.AccessTime,
                text = jamSkema,
                iconTint = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            EventDetailRow(
                icon = Icons.Default.LocationOn,
                text = tempatKerja,
                iconTint = MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(6.dp))

            EventDetailRow(
                icon = Icons.Default.People,
                text = "$jumlahPeserta peserta",
                iconTint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun EventDetailRow(
    icon: ImageVector,
    text: String,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}