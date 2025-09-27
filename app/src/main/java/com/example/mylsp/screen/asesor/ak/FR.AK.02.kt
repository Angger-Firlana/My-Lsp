package com.example.mylsp.screen.asesor.ak

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.model.api.assesment.Ak02Request
import com.example.mylsp.model.api.assesment.Ak02Unit
import com.example.mylsp.model.api.assesment.Apl02
import com.example.mylsp.model.api.assesment.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AK02Manager
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.assesment.AK02ViewModel

@Composable
fun FRAK02(
    modifier: Modifier = Modifier,
    aK02ViewModel: AK02ViewModel,
    idSkema: Int,
    navController: NavController,
    apl02ViewModel: APL02ViewModel
) {
    val context = LocalContext.current
    val ak02Manager = AK02Manager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)

    var isKompeten by remember { mutableStateOf(false) }
    var isBelumKompeten by remember { mutableStateOf(false) }
    var tindakLanjutText by remember { mutableStateOf("") }
    var observasiText by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogFail by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val loading by aK02ViewModel.loading.collectAsState()
    val skemas by apl02ViewModel.apl02.collectAsState()
    val message by aK02ViewModel.message.collectAsState()
    val submissionState by aK02ViewModel.state.collectAsState()
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()

    LaunchedEffect(Unit) {
        apl02ViewModel.getAPL02(idSkema)
    }

    // Handle submission response
    LaunchedEffect(submissionState) {
        submissionState?.let { success ->
            if (success){
                showDialogSuccess = true
                dialogMessage = "Data berhasil dikirim!"
            }else{
                showDialogFail = true
                dialogMessage = message
            }
        }
    }

    skemas?.let { skemas ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderForm(
                "FR.AK.02",
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
                "Beri tanda centang (✔) dikolom yang sesuai untuk mencerminkan bukti yang sesuai untuk setiap Unit Kompetensi.",
                fontSize = 10.sp,
                fontFamily = AppFont.Poppins
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Unit Kompetensi",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            assesmentAsesi?.let {
                UnitKompetensi(
                    assesmentAsesiId = it.id,
                    unitKompetensiList = skemas.data.units
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            RekomendasiHasilAsesmen(
                isKompeten = isKompeten,
                isBelumKompeten = isBelumKompeten,
                onKompetenChanged = { isKompeten = it },
                onBelumKompetenChanged = { isBelumKompeten = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TindakLanjut(
                text = tindakLanjutText,
                onTextChanged = { tindakLanjutText = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ObservasiAsesor(
                text = observasiText,
                onTextChanged = { observasiText = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            KirimButton(
                isLoading = loading
            ) {
                val ak02UnitSubmissions = ak02Manager.getAK02Submission(assesmentAsesi?.id ?: 0)
                aK02ViewModel.postSubmission(
                    Ak02Request(
                        assesment_asesi_id = assesmentAsesi?.id ?: 0,
                        rekomendasi_hasil = if (isKompeten) "kompeten" else if (isBelumKompeten) "tidak_kompeten" else "invalid",
                        ttd_asesi = "belum",
                        ttd_asesor = "sudah",
                        tindak_lanjut = tindakLanjutText,
                        komentar_asesor = observasiText,
                        units = ak02UnitSubmissions
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Success Dialog
    if (showDialogSuccess) {
        AlertDialog(
            onDismissRequest = { showDialogSuccess = false },
            title = {
                Text(
                    text = "Berhasil",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = dialogMessage,
                    fontFamily = AppFont.Poppins
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogSuccess = false
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        "OK",
                        fontFamily = AppFont.Poppins
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
    }

    // Error Dialog
    if (showDialogFail) {
        AlertDialog(
            onDismissRequest = { showDialogFail = false },
            title = {
                Text(
                    text = "Gagal",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            },
            text = {
                Text(
                    text = dialogMessage,
                    fontFamily = AppFont.Poppins
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialogFail = false }
                ) {
                    Text(
                        "OK",
                        fontFamily = AppFont.Poppins,
                        color = Color.Red
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun UnitKompetensi(
    assesmentAsesiId: Int,
    unitKompetensiList: List<UnitApl02>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        unitKompetensiList.forEach { item ->
            UnitKompetensiCard(
                assesmentAsesiId = assesmentAsesiId,
                item = item
            )
        }
    }
}

@Composable
fun UnitKompetensiCard(
    assesmentAsesiId: Int,
    item: UnitApl02,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val ak02Manager = AK02Manager(context = context)

    // State for tracking checkbox selections
    var checkedOptions by remember { mutableStateOf(setOf<String>()) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Initialize checkbox state from saved data
    LaunchedEffect(assesmentAsesiId, item.id) {
        val submissions = ak02Manager.getAK02Submission(assesmentAsesiId)
        val currentSubmission = submissions.firstOrNull { it.unit_id == item.id }
        checkedOptions = currentSubmission?.bukti_yang_relevan
            ?.map { it.bukti_description }
            ?.toSet() ?: setOf()
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.judul_unit,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = AppFont.Poppins,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(standardKompetensiOptions) { option ->
                    val isChecked = checkedOptions.contains(option.text)

                    CheckboxOption(
                        option = option.copy(isChecked = isChecked),
                        isChecked = isChecked,
                        onCheckedChange = { checked ->

                                // Update local state
                                checkedOptions = if (checked) {
                                    checkedOptions + option.text
                                } else {
                                    checkedOptions - option.text
                                }

                                // Save to manager
                                ak02Manager.saveAK02Submission(
                                    assesmentAsesiId = assesmentAsesiId,
                                    unitId = item.id,
                                    bukti = option.text,
                                    isChecked = checked
                                )

                        }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                "Geser Kesamping untuk melihat opsi yang lain",
                fontSize = 10.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Gray
            )
        }
    }

    // Success Dialog (quick feedback)
    if (showSuccessDialog) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500) // Auto dismiss after 1.5 seconds
            showSuccessDialog = false
        }

        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = {
                Text(
                    text = "Berhasil",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                Text(
                    text = dialogMessage,
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showSuccessDialog = false }
                ) {
                    Text(
                        "OK",
                        fontFamily = AppFont.Poppins
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
    }

    // Error Dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = {
                Text(
                    text = "Error",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            },
            text = {
                Text(
                    text = dialogMessage,
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false }
                ) {
                    Text(
                        "OK",
                        fontFamily = AppFont.Poppins,
                        color = Color.Red
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun CheckboxOption(
    option: KompetensiOption,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            1.dp,
            if (isChecked) MaterialTheme.colorScheme.primary
            else Color.Gray.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .clickable { onCheckedChange(!isChecked) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.size(20.dp),
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = option.text,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = if (isChecked) MaterialTheme.colorScheme.onPrimaryContainer
                else Color.Black,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun KirimButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = if (isLoading) "Mengirim..." else "Kirim Data",
            fontSize = 16.sp,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

data class UnitKompetensiItem(
    val title: String,
    val options: List<KompetensiOption>
)

data class KompetensiOption(
    val text: String,
    val isChecked: Boolean = false
)

val standardKompetensiOptions = listOf(
    KompetensiOption("Observasi Demonstrasi"),
    KompetensiOption("Portofolio"),
    KompetensiOption("Pernyataan Pihak Ketiga Pertanyaan Wawancara"),
    KompetensiOption("Pertanyaan Lisan"),
    KompetensiOption("Pertanyaan Tertulis"),
    KompetensiOption("Proyek Kerja"),
    KompetensiOption("Lainnya")
)

@Composable
fun RekomendasiHasilAsesmen(
    isKompeten: Boolean = false,
    isBelumKompeten: Boolean = false,
    onKompetenChanged: (Boolean) -> Unit = {},
    onBelumKompetenChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Rekomendasi hasil Asesmen",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            RekomendasiChecked(
                isKompeten = isKompeten,
                isBelumKompeten = isBelumKompeten,
                onKompetenChanged = onKompetenChanged,
                onBelumKompetenChanged = onBelumKompetenChanged
            )
        }
    }
}

@Composable
fun RekomendasiChecked(
    isKompeten: Boolean = false,
    isBelumKompeten: Boolean = false,
    onKompetenChanged: (Boolean) -> Unit = {},
    onBelumKompetenChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onKompetenChanged(!isKompeten)
                if (!isKompeten) {
                    onBelumKompetenChanged(false)
                }
            }
        ) {
            Checkbox(
                checked = isKompeten,
                onCheckedChange = { checked ->
                    onKompetenChanged(checked)
                    if (checked) {
                        onBelumKompetenChanged(false)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kompeten",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Black
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onBelumKompetenChanged(!isBelumKompeten)
                if (!isBelumKompeten) {
                    onKompetenChanged(false)
                }
            }
        ) {
            Checkbox(
                checked = isBelumKompeten,
                onCheckedChange = { checked ->
                    onBelumKompetenChanged(checked)
                    if (checked) {
                        onKompetenChanged(false)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Belum Kompeten",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Black
            )
        }
    }
}

@Composable
fun TindakLanjut(
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tindak lanjut yang dibutuhkan",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "(masukan pekerjaan tambahan dan asesmen yang diperlukan untuk mencapai kompetensi) :",
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(
                        text = "Masukan tindak lanjut...",
                        fontSize = 14.sp,
                        fontFamily = AppFont.Poppins,
                        color = Color.Gray
                    )
                }
            )
        }
    }
}

@Composable
fun ObservasiAsesor(
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Komentar/ Observasi oleh Asesor",
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(
                        text = "Masukan komentar atau observasi...",
                        fontSize = 14.sp,
                        fontFamily = AppFont.Poppins,
                        color = Color.Gray
                    )
                }
            )
        }
    }
}