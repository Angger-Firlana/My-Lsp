package com.example.mylsp.screen.asesi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.mylsp.R
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.Apl02
import com.example.mylsp.model.api.ElemenAPL02
import com.example.mylsp.model.api.JawabanApl02
import com.example.mylsp.model.api.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.JawabanManager
import com.example.mylsp.viewmodel.APL02ViewModel

@Composable
fun APL02(
    modifier: Modifier = Modifier,
    id: Int,
    apL02ViewModel: APL02ViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val jawabanManager = remember { JawabanManager() }
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val message by apL02ViewModel.message.collectAsState()

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(id)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        apl02?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection()
                SchemaSection(data)
                InstructionCard()
                UnitsSection(data, jawabanManager)
                SubmitButton(jawabanManager)
            }
        }?: kotlin.run {
            LoadingScreen()
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logoheader),
                contentDescription = "Logo Header"
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

    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun SchemaSection(data: Apl02) {
    Text(
        text = "Skema Sertifikasi",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Column(
        modifier = Modifier.padding(start = 20.dp)
    ) {
        Text(
            text = "Judul Skema : ${data.judul_skema.orEmpty()}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            text = "Nomor Skema : ${data.nomor_skema.orEmpty()}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun InstructionCard() {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
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

            val instructions = listOf(
                "Baca setiap pertanyaan di kolom sebelah kiri.",
                "Beri tanda centang pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                "Isi kolom di sebelah kanan dengan mendaftar bukti yang Anda miliki untuk menunjukkan bahwa Anda melakukan tugas-tugas ini."
            )

            instructions.forEach { instruction ->
                Text(
                    text = instruction,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun UnitsSection(
    data: Apl02,
    jawabanManager: JawabanManager
) {
    data.data?.forEach { unit ->
        UnitCompetensiSection(unit, jawabanManager)
    }
}

@Composable
private fun UnitCompetensiSection(
    unit: UnitApl02,
    jawabanManager: JawabanManager
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
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
                text = "Kode Unit : ${unit.kode_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Judul Unit : ${unit.judul_unit}",
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
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    unit.elemen.forEach { (index, elemen) ->
        ElemenCard(
            index = index,
            elemen = elemen,
            jawabanManager = jawabanManager
        )
    }
}

@Composable
private fun ElemenCard(
    index: String,
    elemen: ElemenAPL02,
    jawabanManager: JawabanManager
) {
    var selectedOption by remember { mutableStateOf("") }
    val options = listOf("K", "BK")
    val buktiRelevans = listOf(
        "Fotocopy semester 1-5",
        "Sertifikat PKL"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            elemen.kuk.forEach { kuk ->
                Text(
                    text = "${elemen.elemen_index}.${kuk.urutan}.${kuk.deskripsi_kuk}",
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }

    // Radio Button Selection
    RadioButtonGroup(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { option ->
            selectedOption = option
            jawabanManager.addJawaban(
                idElemen = index.toInt(),
                jawaban = option
            )
        }
    )

    // Bukti Relevan Section
    BuktiRelevanSection(
        buktiList = buktiRelevans,
        onBuktiChanged = { bukti, isChecked ->
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
}

@Composable
private fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Text(
                    text = option,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun BuktiRelevanSection(
    buktiList: List<String>,
    onBuktiChanged: (String, Boolean) -> Unit
) {
    Text(
        text = "Bukti Relevan",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        buktiList.forEach { bukti ->
            BuktiCheckboxItem(
                bukti = bukti,
                onCheckedChange = { isChecked ->
                    onBuktiChanged(bukti, isChecked)
                }
            )
        }
    }
}

@Composable
private fun BuktiCheckboxItem(
    bukti: String,
    onCheckedChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                checked = !checked
                onCheckedChange(checked)
            }
            .padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked ->
                checked = isChecked
                onCheckedChange(isChecked)
            }
        )
        Text(
            text = bukti,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun SubmitButton(jawabanManager: JawabanManager) {
    Button(
        onClick = {
            // TODO: Implement submit logic
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Kirim Jawaban",
            fontFamily = AppFont.Poppins
        )
    }
}