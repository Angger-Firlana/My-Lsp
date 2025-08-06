package com.example.mylsp.screen.asesor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.MukViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SkemaListScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: MukViewModel = viewModel()
    val skemaList by viewModel.skemas.collectAsState()

    LaunchedEffect(true) {
        viewModel.getSkemas()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(220.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {}

        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pilih Event Yang Terjadwal",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(skemaList.size) { index ->
                    val skema = skemaList[index]
                    val dateSkema = LocalDate.parse(skema.tanggalBerlaku)
                    val tanggalSkema = dateSkema.format(
                        DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale("id", "ID"))
                    )

                    val jamSkema = "09:00 - 12:00"
                    val tempatKerja = "Lab"
                    val jumlahPeserta = "72"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            Text(
                                skema.judulSkema,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    tanggalSkema,
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.AccountCircle, contentDescription = null)
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    "$jamSkema ($tempatKerja)",
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.AccountCircle, contentDescription = null)
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    jumlahPeserta,
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}