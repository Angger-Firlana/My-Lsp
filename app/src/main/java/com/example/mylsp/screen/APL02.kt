package com.example.mylsp.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.component.ErrorCard
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.assesment.Apl02
import com.example.mylsp.model.api.assesment.ElemenAPL02
import com.example.mylsp.model.api.assesment.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.JawabanManager
import com.example.mylsp.viewmodel.APL02ViewModel

@Composable
fun APL02(
    modifier: Modifier = Modifier,
    id: Int,
    apL02ViewModel: APL02ViewModel,
    nextForm: () -> Unit
) {
    val context = LocalContext.current
    val jawabanManager = remember { JawabanManager() }
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val message by apL02ViewModel.message.collectAsState()
    val state by apL02ViewModel.state.collectAsState()
    var isSubmitted by remember {mutableStateOf(false)}

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(id)
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success) run {
                nextForm()
            }
            apL02ViewModel.resetState()
        }
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
                HeaderForm(
                    title = "FR.APL.02.ASESMEN MANDIRI"
                )
                SchemaSection(data)
                InstructionCard()
                UnitsSection(id, data, jawabanManager)
                SubmitButton(apL02ViewModel, nextForm, titleButton = "Kirim Jawaban")
                if (message.isNotEmpty()){
                    ErrorCard(
                        errorMessage = message,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } ?: run {
            LoadingScreen()
        }
    }
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
    skemaId: Int,
    data: Apl02,
    jawabanManager: JawabanManager
) {
    data.data.forEach { unit ->
        UnitCompetensiSection(
            skemaId = skemaId,
            unit = unit,
            jawabanManager = jawabanManager
        )
    }
}

@Composable
private fun UnitCompetensiSection(
    skemaId: Int,
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
            color = Color.Black
        )

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(
                text = "Kode Unit : ${unit.kode_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
            Text(
                text = "Judul Unit : ${unit.judul_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
        }
    }

    Text(
        text = "Dapatkah Saya?",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    unit.elemen.forEach { (_, elemen) ->
        ElemenCard(
            elemen = elemen
        ) { buktiRelevans, kompetensinitas ->
            // langsung panggil util
            jawabanManager.upsertElemen(
                skemaId = skemaId,
                unitKe = unit.unit_ke,
                kodeUnit = unit.kode_unit,
                elemenId = elemen.elemen_index,
                kompetensinitas = kompetensinitas,
                buktiList = buktiRelevans
            )
        }
    }
}

@Composable
private fun ElemenCard(
    elemen: ElemenAPL02,
    onSubmit: (List<String>, String) -> Unit
) {
    var selectedOption by remember { mutableStateOf("") }
    val buktiSelected = remember { mutableStateListOf<String>() }

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

    // Radio Button Section
    RadioButtonGroup(
        options = listOf("k", "bk"),
        selectedOption = selectedOption,
        onOptionSelected = { option ->
            selectedOption = option
            onSubmit(buktiSelected, selectedOption)
        }
    )

    // Bukti Relevan Section
    BuktiRelevanSection(
        buktiList = buktiRelevans,
        onBuktiChanged = { bukti, isChecked ->
            if (isChecked) {
                buktiSelected.add(bukti)
            } else {
                buktiSelected.remove(bukti)
            }
            onSubmit(buktiSelected, selectedOption)
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
                    text = option.uppercase(),
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
private fun SubmitButton(apL02ViewModel: APL02ViewModel, nextForm: () -> Unit, titleButton: String = "Kirim Jawaban", buttonUnApproved: String?= null) {
    Button(
        onClick = {
            if(buttonUnApproved != null){
                // val jawaban = com.example.mylsp.util.Util.jawabanApl02.value
                // apL02ViewModel.sendApl02()
                // Log.d("APL02", "Jawaban: $jawaban")
            }
             nextForm()

        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = titleButton,
            fontFamily = AppFont.Poppins
        )
    }

    if (buttonUnApproved!=null){
        Button(
            onClick = {
                nextForm()
//            val jawaban = com.example.mylsp.util.Util.jawabanApl02.value
//            apL02ViewModel.sendApl02()
//            Log.d("APL02", "Jawaban: $jawaban")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = titleButton,
                fontFamily = AppFont.Poppins
            )
        }
    }
}
