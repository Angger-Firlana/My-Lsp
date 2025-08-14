package com.example.mylsp.screen.asesor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylsp.model.api.SkemaDetail
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.UserManager
import com.example.mylsp.util.Util
import com.example.mylsp.viewmodel.MukViewModel
import com.example.mylsp.viewmodel.SkemaViewModel
import com.example.mylsp.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SkemaListScreen(
    modifier: Modifier = Modifier,
    skemaViewModel: SkemaViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    var search by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Semua Event") }

    val skemas by skemaViewModel.skemas.collectAsState()

    val filterOptions = listOf("Semua Event", "Event Hari Ini", "Event Minggu Ini", "Event Bulan Ini")

    LaunchedEffect(Unit) {
        Log.d("User", "")
        skemaViewModel.getListSkema()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (true){
            Box(modifier = Modifier.fillMaxSize()) {
                // Rounded Header with Tertiary Color
                Card(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .height(280.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(20.dp)
                    ) {
                        Column {
                            // Title section - more compact
                            Text(
                                text = "Event Terjadwal",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Text(
                                text = "Pilih event yang ingin Anda ikuti",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Simplified Search Bar
                            OutlinedTextField(
                                value = search,
                                onValueChange = { search = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(
                                        "Cari event...",
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                        fontSize = 14.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                trailingIcon = {
                                    Box {
                                        IconButton(onClick = { expanded = true }) {
                                            Icon(
                                                Icons.Default.FilterList,
                                                contentDescription = "Filter",
                                                tint = if (selectedFilter != "Semua Event")
                                                    MaterialTheme.colorScheme.onPrimary else
                                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false }
                                        ) {
                                            filterOptions.forEach { option ->
                                                DropdownMenuItem(
                                                    text = {
                                                        Text(
                                                            option,
                                                            fontSize = 14.sp,
                                                            fontFamily = AppFont.Poppins
                                                        )
                                                    },
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
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.primary,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.95f)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            // Compact filter indicator
                            if (selectedFilter != "Semua Event") {
                                Text(
                                    text = "• $selectedFilter",
                                    fontFamily = AppFont.Poppins,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Event List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        top = 200.dp,
                        bottom = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(skemas.size){
                        val skema = skemas[it]
                        SkemaCard(
                            skema = skema,
                            onCardClick = {
                                val role = userManager.getUserRole()
                                if(role == "asesi"){
                                    navController.navigate("apl02/${skema.id}")
                                }else if (role == "asesor"){
                                    navController.navigate("ia01/${skema.id}")
                                }
                            }
                        )
                    }
                }
            }
        }else{
            WaitingApprovalScreen(modifier, navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SkemaCard(
    skema: SkemaDetail,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val judulSkema = skema.judul_skema
    val tanggalBerlaku = "2025-02-01"
    val nomorSkema = skema.nomor_skema
    val jurusan = skema.jurusan
    val totalUnits = skema.total_units
    val total_elements = skema.total_elements
    val totalKuk = skema.total_kuk

    val dateSkema = LocalDate.parse(tanggalBerlaku)
    val tanggalSkema = dateSkema.format(
        DateTimeFormatter.ofPattern("d MMM yyyy", Locale("id", "ID"))
    )

    val jamSkema = "09:00"
    val tempatKerja = "Lab Komputer"
    val jumlahPeserta = "72"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title with modern styling
            Text(
                text = judulSkema,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Simplified info grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left column
                Column(modifier = Modifier.weight(1f)) {
                    EventDetailRow(
                        icon = Icons.Default.DateRange,
                        text = tanggalSkema,
                        iconTint = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    EventDetailRow(
                        icon = Icons.Default.LocationOn,
                        text = tempatKerja,
                        iconTint = MaterialTheme.colorScheme.tertiary,
                        fontSize = 13.sp
                    )
                }

                // Right column
                Column(modifier = Modifier.weight(1f)) {
                    EventDetailRow(
                        icon = Icons.Default.AccessTime,
                        text = jamSkema,
                        iconTint = MaterialTheme.colorScheme.secondary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    EventDetailRow(
                        icon = Icons.Default.People,
                        text = "$jumlahPeserta peserta",
                        iconTint = MaterialTheme.colorScheme.outline,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailRow(
    icon: ImageVector,
    text: String,
    iconTint: Color,
    fontSize: TextUnit = 13.sp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = text,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = (fontSize.value * 1.3).sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}