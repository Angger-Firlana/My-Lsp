package com.example.mylsp.screen.asesi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.util.AppFont
import com.example.mylsp.R
import com.example.mylsp.model.api.JawabanApl02
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.util.JawabanManager

@Composable
fun APL02(modifier: Modifier = Modifier,id:Int, apL02ViewModel: APL02ViewModel, navController: NavController) {
    val context = LocalContext.current
    val jawabanManager = JawabanManager()
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val message by apL02ViewModel.message.collectAsState()
    val pilihan = listOf("K", "BK")

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(1/10f).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logoheader),
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.weight(3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "FR.APL.02.ASESMEN MANDIRI",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.padding(vertical = 16.dp))

            apl02?.let {
                Text(
                    text = "Skema Sertifikasi",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Text(
                        text = "Judul Skema : ${apl02?.judul_skema?:""} " ,
                        fontFamily = AppFont.Poppins,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "Nomor Skema : ${apl02?.nomor_skema?:""} " ,
                        fontFamily = AppFont.Poppins,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp ,start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(0.7f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "PANDUAN ASSESMEN MANDIRI",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Instruksi",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color(0xFF666666),
                            lineHeight = 16.sp
                        )

                        Text(
                            text = " Baca setiap pertanyaan di kolom sebelah kiri.",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )

                        Text(
                            text = " Beri tanda centang pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )

                        Text(
                            text = " Isi kolom di sebelah kanan dengan mendaftar bukti yang Anda miliki untuk menunjukkan bahwa Anda melakukan tugas-tugas ini.",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )

                    }
                }

                apl02?.data?.let { units ->
                    units.forEach { unit ->
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Unit Kompetensi ${unit.unit_ke}",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Column(
                                modifier = Modifier.padding(start = 20.dp)
                            ) {
                                Text(
                                    text = "Kode Unit : ${unit.kode_unit} ",
                                    fontFamily = AppFont.Poppins,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Judul Skema : ${unit.judul_unit} ",
                                    fontFamily = AppFont.Poppins,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                        }
                        Text(
                            text = "Dapatkah Saya?",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )


                        unit.elemen.forEach { (index, elemen) ->
                            val kuk = elemen.kuk
                            val buktiRelevans = listOf(
                                "Fotocopy semester 1-5",
                                "Sertifikat PKL"
                            )
                            var dipilih by remember { mutableStateOf("") }

                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                ),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    Text(
                                        text = "Elemen ${elemen.elemen_index}: ${elemen.nama_elemen}",
                                        fontFamily = AppFont.Poppins,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Kriteria Untuk Kerja:",
                                        fontFamily = AppFont.Poppins,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )

                                    kuk.forEach { kuk->
                                        Text(
                                            text = "${elemen.elemen_index}.${kuk.urutan}.${kuk.deskripsi_kuk} ",
                                            fontFamily = AppFont.Poppins,
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )
                                    }


                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                pilihan.forEach{ s->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = s == dipilih,
                                            onClick = {
                                                dipilih = s
                                                JawabanManager().addJawaban(idElemen = index.toInt(), jawaban = dipilih)
                                            }
                                        )
                                        Text(
                                            s,
                                            fontFamily = AppFont.Poppins,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp
                                        )
                                    }

                                }
                            }

                            Text("Bukti Relevan", fontFamily = AppFont.Poppins, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp))


                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp),
                            ) {
                                buktiRelevans.forEach { bukti ->
                                    var checked by remember { mutableStateOf(false) }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                checked = !checked
                                                if (checked) {
                                                    jawabanManager.addBukti(
                                                        idElemen = elemen.elemen_index,
                                                        bukti = bukti
                                                    )
                                                } else {
                                                    jawabanManager.removeBukti(
                                                        idElemen = elemen.elemen_index,
                                                        bukti = bukti
                                                    )
                                                }
                                            }
                                    ) {
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { isChecked ->
                                                checked = isChecked
                                                if (isChecked) {
                                                    jawabanManager.addBukti(
                                                        idElemen = elemen.elemen_index,
                                                        bukti = bukti
                                                    )
                                                } else {
                                                    jawabanManager.removeBukti(
                                                        idElemen = elemen.elemen_index,
                                                        bukti = bukti
                                                    )
                                                }
                                            }
                                        )
                                        Text(bukti, fontFamily = AppFont.Poppins)
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}