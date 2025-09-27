package com.example.mylsp.screen.asesor.ak

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.model.api.assesment.KomponenData
import com.example.mylsp.model.api.assesment.PostAK03Request
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AK03SubmissionManager
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.viewmodel.assesment.AK03ViewModel
import com.example.mylsp.viewmodel.assesment.KomponenViewModel

@Composable
fun FRAK03(
    modifier: Modifier = Modifier,
    aK03ViewModel: AK03ViewModel,
    komponenViewModel: KomponenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val assesmentAsesiManager = AssesmentAsesiManager(context)

    val listKomponen by komponenViewModel.listKomponen.collectAsState()
    val aK03SubmissionManager = AK03SubmissionManager(context)
    val state by aK03ViewModel.state.collectAsState()
    val message by aK03ViewModel.message.collectAsState()
    var showDialogSuccess by remember { mutableStateOf(false) }
    var catatanTambahan by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        komponenViewModel.getListKomponen()
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success){
                showDialogSuccess = true
                navController.popBackStack()
            }else{
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                showDialogSuccess = false
            }
            aK03ViewModel.resetState()
        }
    }



    Box(modifier = modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderForm(
                "FR.AK.03",
                "PERSETUJUAN ASESMEN DAN KERAHASIAAN"
            )

            SkemaSertifikasi(
                judulUnit = "Okupasi Junior Custom Made",
                kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
                TUK = "Sewaktu/Tempat Kerja/Mandiri",
                namaAsesor = null,
                namaAsesi = null,
                tanggalAsesmen = null
            )

            Text(
                "Umpan balik dari asesi (diisi oleh Asesi setelah pengambilan keputusan)",
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Komponen(
                assesmentAsesiId = assesmentAsesiManager.getAssesmentId(),
                komponenList = listKomponen,
                navController = navController,
                aK03SubmissionManager = aK03SubmissionManager
            )

            CatatanField(
                value = catatanTambahan,
                height = 200.dp,
                onValueChange = { catatanTambahan = it }
            )

            KirimButton {
                aK03ViewModel.sendSubmissionAK03(
                    PostAK03Request(
                        assesment_asesi_id = assesmentAsesiManager.getAssesmentId(),
                        catatan_tambahan = catatanTambahan,
                        komponen = aK03SubmissionManager.getAllSubmissionAK03(assesment_asesi_id = assesmentAsesiManager.getAssesmentId())
                    )
                )
                Log.d("AK03Submission", aK03SubmissionManager.getAllSubmissionAK03(assesment_asesi_id = assesmentAsesiManager.getAssesmentId()).toString())
            }
        }
    }

}


@Composable
fun Komponen(
    modifier: Modifier = Modifier,
    assesmentAsesiId: Int,
    komponenList: List<KomponenData>,
    aK03SubmissionManager: AK03SubmissionManager,
    navController: NavController
) {

    val checkboxStates = remember { mutableStateMapOf<Int, String?>() }
    val catatanStates = remember { mutableStateMapOf<Int, String?>()}
    // Load existing submissions
    LaunchedEffect(komponenList) {
        val existingSubmissions = aK03SubmissionManager.getAllSubmissionAK03(assesmentAsesiId)
        komponenList.forEach { komponen ->
            val existingSubmission = existingSubmissions.find { it.komponen_id == komponen.id }
            if (existingSubmission != null) {
                catatanStates[komponen.id] = existingSubmission.catatan_asesi
                checkboxStates[komponen.id] = existingSubmission.hasil
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        KomponenList(
            items = komponenList,
            checkboxStates = checkboxStates,
            catatanStates = catatanStates,
            assesmentAsesiId = assesmentAsesiId,
            aK03SubmissionManager = aK03SubmissionManager,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun KomponenList(
    items: List<KomponenData>,
    checkboxStates: MutableMap<Int, String?>,
    catatanStates: MutableMap<Int, String?>,
    assesmentAsesiId: Int,
    aK03SubmissionManager: AK03SubmissionManager
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Komponen",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEachIndexed { index, item ->
                var catatanAsesi by remember { mutableStateOf("") }
                KomponenItem(
                    komponen = item,
                    checkboxState = checkboxStates[item.id],
                    onCheckboxChange = { hasil ->
                        checkboxStates[item.id] = hasil
                        aK03SubmissionManager.saveAK03Submission(
                            assesment_asesi_id = assesmentAsesiId,
                            komponen = item,
                            hasil = hasil,
                            catatanAsesi = catatanStates[item.id] ?: ""
                        )
                    }
                )

                CatatanField(
                    value = catatanStates[item.id]?: "",
                    onValueChange = {
                        catatanStates[item.id] = it
                        aK03SubmissionManager.saveAK03Submission(
                            assesment_asesi_id = assesmentAsesiId,
                            komponen = item,
                            hasil = checkboxStates[item.id] ?: "",
                            catatanAsesi = it
                        )
                    }
                )

                if (index < items.size - 1) {
                    HorizontalDivider(
                        color = Color.Gray.copy(alpha = 0.2f),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KomponenItem(
    komponen: KomponenData,
    checkboxState: String?,
    onCheckboxChange: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "• ",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = komponen.komponen,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                lineHeight = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckboxHasilOption(
                label = "Ya",
                checked = checkboxState == "Ya",
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        onCheckboxChange("Ya")
                    }
                }
            )

            Spacer(modifier = Modifier.width(24.dp))

            CheckboxHasilOption(
                label = "Tidak",
                checked = checkboxState == "Tidak",
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        onCheckboxChange("Tidak")
                    }
                }
            )
        }
    }
}

@Composable
fun CheckboxHasilOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray
            )
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun CatatanField(
    value: String,
    height: Dp = 100.dp,
    onValueChange: (String) -> Unit
) {
    Text(
        text = "Catatan/ komentar lainnya (apabila ada)",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                "Ketikkan komentar anda di sini.",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun KirimButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                "Kirim Jawaban",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )
        }
    }
}