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
            }else{
                WaitingApprovalScreen(modifier, navController)
            }

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
    // Extract data from skema - adjust these according to your actual data structure
    val judulSkema = skema.judul_skema // skema.judulSkema
    val tanggalBerlaku = "2025-02-01"
    val nomorSkema = skema.nomor_skema
    val jurusan = skema.jurusan
    val totalUnits = skema.total_units
    val total_elements = skema.total_elements
    val totalKuk = skema.total_kuk

    val dateSkema = LocalDate.parse(tanggalBerlaku)
    val tanggalSkema = dateSkema.format(
        DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale("id", "ID"))
    )

    val jamSkema = "09:00"
    val tempatKerja = "Lab Komputer"
    val jumlahPeserta = "72"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Title
            Text(
                text = judulSkema,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Event Details
            EventDetailRow(
                icon = Icons.Default.DateRange,
                text = tanggalSkema,
                iconTint = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            EventDetailRow(
                icon = Icons.Default.AccessTime,
                text = jamSkema,
                iconTint = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            EventDetailRow(
                icon = Icons.Default.LocationOn,
                text = tempatKerja,
                iconTint = MaterialTheme.colorScheme.tertiary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            EventDetailRow(
                icon = Icons.Default.People,
                text = "$jumlahPeserta peserta",
                iconTint = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun EventDetailRow(
    icon: ImageVector,
    text: String,
    iconTint: Color,
    fontSize: TextUnit = 12.sp,
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
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = (fontSize.value * 1.2).sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}