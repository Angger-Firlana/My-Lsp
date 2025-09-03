package com.example.mylsp.screen.asesor

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.Apl02
import com.example.mylsp.model.api.ElemenAPL02
import com.example.mylsp.model.api.KriteriaUntukKerja
import com.example.mylsp.model.api.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.JawabanManager
import com.example.mylsp.viewmodel.APL02ViewModel

@Composable
fun FRIA01(
    modifier: Modifier = Modifier,
    idSkema: Int,
    apL02ViewModel: APL02ViewModel,
    nextForm: ()-> Unit
) {
    val context = LocalContext.current
    val jawabanManager = remember { JawabanManager() }
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val message by apL02ViewModel.message.collectAsState()
    val pilihan = listOf("IYA", "TIDAK")

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(idSkema)
    }


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        apl02?.let { apl02Data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderForm(
                    title = "FR.IA.01.CL",
                    subTitle = "CEKLIS OBSERVASI AKTIVITAS DI TEMPAT KERJA ATAU TEMPAT KERJA SIMULASI"
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                SchemaInfoSection(apl02Data)

                InstructionsCard()

                UnitsSection(
                    units = apl02Data.data,
                    pilihan = pilihan,
                    jawabanManager = jawabanManager
                )


                SubmitButton(nextForm)
            }
        }?: kotlin.run {
            LoadingScreen()
        }
    }
}

@Composable
private fun SchemaInfoSection(apl02Data: Apl02) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Skema Sertifikasi",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Judul Skema: ${apl02Data.judul_skema ?: ""}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Nomor Skema: ${apl02Data.nomor_skema ?: ""}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun InstructionsCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "PANDUAN ASESMEN MANDIRI",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Instruksi:",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color(0xFF666666)
            )

            val instructions = listOf(
                "1. Baca setiap pertanyaan di kolom sebelah kiri.",
                "2. Beri tanda centang pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                "3. Isi kolom di sebelah kanan dengan mendaftar bukti yang Anda miliki untuk menunjukkan bahwa Anda melakukan tugas-tugas ini."
            )

            instructions.forEach { instruction ->
                Text(
                    text = instruction,
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun UnitsSection(
    units: List<UnitApl02>?,
    pilihan: List<String>,
    jawabanManager: JawabanManager
) {
    units?.forEach { unit ->
        UnitCard(
            unit = unit,
            pilihan = pilihan,
            jawabanManager = jawabanManager
        )
    }
}

@Composable
private fun UnitCard(
    unit: UnitApl02,
    pilihan: List<String>,
    jawabanManager: JawabanManager
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Unit Header
        Text(
            text = "Unit Kompetensi ${unit.unit_ke}",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )

        Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
            Text(
                text = "Kode Unit: ${unit.kode_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Judul Unit: ${unit.judul_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Penilaian",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        unit.elemen.forEach { (index, elemen) ->
            ElementCard(
                elemen = elemen,
                index = index,
                pilihan = pilihan,
                jawabanManager = jawabanManager
            )
        }
    }
}

@Composable
private fun ElementCard(
    elemen: ElemenAPL02,
    index: String,
    pilihan: List<String>,
    jawabanManager: JawabanManager
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Element Title
            Text(
                text = "Elemen ${elemen.elemen_index}: ${elemen.nama_elemen}",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kriteria Untuk Kerja:",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.Black
            )

            // KUK Items
            elemen.kuk.forEach { kukItem ->
                KUKItem(
                    kukItem = kukItem,
                    elemenIndex = elemen.elemen_index,
                    pilihan = pilihan,
                    jawabanManager = jawabanManager
                )
            }
        }
    }
}

@Composable
private fun KUKItem(
    kukItem: KriteriaUntukKerja,
    elemenIndex: Int,
    pilihan: List<String>,
    jawabanManager: JawabanManager
) {
    var selectedOption by remember { mutableStateOf("") }
    var textFieldValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // KUK Description
        Text(
            text = "${elemenIndex}.${kukItem.urutan}. ${kukItem.deskripsi_kuk}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Radio Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            pilihan.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option == selectedOption,
                        onClick = {
                            selectedOption = option
                        }
                    )
                    Text(
                        text = option,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Text Field for additional notes
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = {
                Text(
                    "Penilaian Lanjut",
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
private fun EvidenceSection(
    elemenIndex: Int,
    jawabanManager: JawabanManager
) {
    val buktiRelevans = listOf(
        "Fotocopy semester 1-5",
        "Sertifikat PKL",
        "Portofolio",
        "Sertifikat pelatihan"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Bukti Relevan",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        buktiRelevans.forEach { bukti ->
            var checked by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        checked = !checked
                        if (checked) {
                        } else {
                        }
                    }
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        checked = isChecked
                        if (isChecked) {
                        } else {
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = bukti,
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun SubmitButton(
    nextForm: ()-> Unit
) {
    Button(
        onClick = {
            nextForm()
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Kirim Penilaian",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}