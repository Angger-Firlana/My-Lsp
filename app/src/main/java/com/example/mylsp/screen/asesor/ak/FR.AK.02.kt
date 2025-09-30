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
import com.example.mylsp.model.api.assesment.AK02GetBukti
import com.example.mylsp.model.api.assesment.Ak02Request
import com.example.mylsp.model.api.assesment.Ak02Unit
import com.example.mylsp.model.api.assesment.AK02GetSubmission
import com.example.mylsp.model.api.assesment.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AK02Manager
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.user.UserManager
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
    val userManager = UserManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()

    // Validasi role user
    val userRole = userManager.getUserRole()
    val isAsesor = userRole.equals("asesor", ignoreCase = true) || userRole.equals("assesor", ignoreCase = true)

    var isKompeten by remember { mutableStateOf(false) }
    var isBelumKompeten by remember { mutableStateOf(false) }
    var tindakLanjutText by remember { mutableStateOf("") }
    var observasiText by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogFail by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var showApproveDialog by remember { mutableStateOf(false) }

    val loading by aK02ViewModel.loading.collectAsState()
    val skemas by apl02ViewModel.apl02.collectAsState()
    val message by aK02ViewModel.message.collectAsState()
    val ak02Submissions by aK02ViewModel.submission.collectAsState()
    val submissionState by aK02ViewModel.state.collectAsState()
//    val updateTtdState by aK02ViewModel.updateTtdState.collectAsState()

    // Cek apakah sudah ada submission
    val hasSubmission = ak02Submissions != null
    val currentSubmission = ak02Submissions
    val isFormReadOnly = hasSubmission

    LaunchedEffect(Unit) {
        apl02ViewModel.getAPL02(idSkema)
        aK02ViewModel.getSubmission(assesmentAsesi?.id ?: 0)
    }

    // Load data dari submission jika ada
    LaunchedEffect(currentSubmission) {
        currentSubmission?.let { submission ->
            isKompeten = submission.rekomendasi_hasil == "kompeten"
            isBelumKompeten = submission.rekomendasi_hasil == "tidak_kompeten"
            tindakLanjutText = submission.tindak_lanjut ?: ""
//            observasiText = submission.komentar_asesor ?: ""
        }
    }

    // Handle submission response
    LaunchedEffect(submissionState) {
        submissionState?.let { success ->
            if (success) {
                showDialogSuccess = true
                dialogMessage = "Data berhasil dikirim!"
            } else {
                showDialogFail = true
                dialogMessage = message
            }
        }
    }

    // Handle update TTD response
//    LaunchedEffect(updateTtdState) {
//        updateTtdState?.let { success ->
//            if (success) {
//                showDialogSuccess = true
//                dialogMessage = "Persetujuan berhasil dikirim!"
//                aK02ViewModel.getSubmission(assesmentAsesi?.id ?: 0)
//            } else {
//                showDialogFail = true
//                dialogMessage = message
//            }
//            aK02ViewModel.resetUpdateTtdState()
//        }
//    }

    // Validasi akses untuk non-asesor
    if (!isAsesor && !hasSubmission) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Akses Ditolak",
                fontSize = 20.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hanya Asesor yang dapat mengisi form ini",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Gray
            )
        }
        return
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

            // Status badge jika form sudah disubmit
            if (hasSubmission) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Form telah disubmit",
                                fontSize = 14.sp,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Status TTD Asesi: ${currentSubmission?.ttd_asesi ?: "belum"}",
                                fontSize = 12.sp,
                                fontFamily = AppFont.Poppins,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

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
                    unitKompetensiList = skemas.data.units,
                    isReadOnly = isFormReadOnly,
                    existingSubmission = currentSubmission
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            RekomendasiHasilAsesmen(
                isKompeten = isKompeten,
                isBelumKompeten = isBelumKompeten,
                onKompetenChanged = { if (!isFormReadOnly) isKompeten = it },
                onBelumKompetenChanged = { if (!isFormReadOnly) isBelumKompeten = it },
                isEnabled = !isFormReadOnly
            )

            Spacer(modifier = Modifier.height(16.dp))

            TindakLanjut(
                text = tindakLanjutText,
                onTextChanged = { if (!isFormReadOnly) tindakLanjutText = it },
                isEnabled = !isFormReadOnly
            )

            Spacer(modifier = Modifier.height(16.dp))

            ObservasiAsesor(
                text = observasiText,
                onTextChanged = { if (!isFormReadOnly) observasiText = it },
                isEnabled = !isFormReadOnly
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tampilkan button sesuai role dan status
            if (isAsesor && !isFormReadOnly) {
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
            } else if (!isAsesor && hasSubmission && currentSubmission?.ttd_asesi == "belum") {
                // Button approve untuk asesi
                Button(
                    onClick = { showApproveDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !loading
                ) {
                    Text(
                        text = if (loading) "Memproses..." else "Setujui Asesmen",
                        fontSize = 16.sp,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Approve Dialog untuk Asesi
    if (showApproveDialog) {
        AlertDialog(
            onDismissRequest = { showApproveDialog = false },
            title = {
                Text(
                    text = "Konfirmasi Persetujuan",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin menyetujui hasil asesmen ini? Tindakan ini tidak dapat dibatalkan.",
                    fontFamily = AppFont.Poppins
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showApproveDialog = false
                        currentSubmission?.let { submission ->
//                            aK02ViewModel.updateTtdAsesi(submission.id)
                        }
                    }
                ) {
                    Text(
                        "Ya, Setuju",
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showApproveDialog = false }
                ) {
                    Text(
                        "Batal",
                        fontFamily = AppFont.Poppins
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
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
    isReadOnly: Boolean = false,
    existingSubmission: AK02GetSubmission? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        unitKompetensiList.forEach { item ->
            // Ambil SEMUA bukti untuk unit ini dari details yang sesuai
            val submittedBukti = existingSubmission?.details
                ?.firstOrNull { it.unit_id == item.id }  // Cari detail untuk unit ini
                ?.bukti ?: emptyList()  // Ambil list bukti nya

            UnitKompetensiCard(
                assesmentAsesiId = assesmentAsesiId,
                item = item,
                isReadOnly = isReadOnly,
                submittedBukti = submittedBukti
            )
        }
    }
}

@Composable
fun UnitKompetensiCard(
    assesmentAsesiId: Int,
    item: UnitApl02,
    isReadOnly: Boolean = false,
    submittedBukti: List<AK02GetBukti>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val ak02Manager = AK02Manager(context = context)

    var checkedOptions by remember { mutableStateOf(setOf<String>()) }

    // Initialize checkbox state
    LaunchedEffect(assesmentAsesiId, item.id, submittedBukti) {
        checkedOptions = if (isReadOnly && submittedBukti.isNotEmpty()) {
            submittedBukti.map { it.bukti_description }.toSet()
        } else {
            val submissions = ak02Manager.getAK02Submission(assesmentAsesiId)
            val currentSubmission = submissions.firstOrNull { it.unit_id == item.id }
            currentSubmission?.bukti_yang_relevan
                ?.map { it.bukti_description }
                ?.toSet() ?: setOf()
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isReadOnly) Color.Gray.copy(alpha = 0.1f) else Color.White
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
                        isEnabled = !isReadOnly,
                        onCheckedChange = { checked ->
                            if (!isReadOnly) {
                                checkedOptions = if (checked) {
                                    checkedOptions + option.text
                                } else {
                                    checkedOptions - option.text
                                }

                                ak02Manager.saveAK02Submission(
                                    assesmentAsesiId = assesmentAsesiId,
                                    unitId = item.id,
                                    bukti = option.text,
                                    isChecked = checked
                                )
                            }
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
}

@Composable
fun CheckboxOption(
    option: KompetensiOption,
    isChecked: Boolean,
    isEnabled: Boolean = true,
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
                .clickable(enabled = isEnabled) { onCheckedChange(!isChecked) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = if (isEnabled) onCheckedChange else null,
                enabled = isEnabled,
                modifier = Modifier.size(20.dp),
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = MaterialTheme.colorScheme.primary,
                    disabledCheckedColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
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
        shape = RoundedCornerShape(12.dp),
        enabled = !isLoading
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
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color.Gray.copy(alpha = 0.1f)
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
                onBelumKompetenChanged = onBelumKompetenChanged,
                isEnabled = isEnabled
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
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(enabled = isEnabled) {
                onKompetenChanged(!isKompeten)
                if (!isKompeten) {
                    onBelumKompetenChanged(false)
                }
            }
        ) {
            Checkbox(
                checked = isKompeten,
                onCheckedChange = if (isEnabled) { checked ->
                    onKompetenChanged(checked)
                    if (checked) {
                        onBelumKompetenChanged(false)
                    }
                } else null,
                enabled = isEnabled,
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
            modifier = Modifier.clickable(enabled = isEnabled) {
                onBelumKompetenChanged(!isBelumKompeten)
                if (!isBelumKompeten) {
                    onKompetenChanged(false)
                }
            }
        ) {
            Checkbox(
                checked = isBelumKompeten,
                onCheckedChange = if (isEnabled) { checked ->
                    onBelumKompetenChanged(checked)
                    if (checked) {
                        onKompetenChanged(false)
                    }
                } else null,
                enabled = isEnabled,
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
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color.Gray.copy(alpha = 0.1f)
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
                enabled = isEnabled,
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
                    unfocusedTextColor = Color.Black,
                    disabledBorderColor = Color.Gray.copy(alpha = 0.3f),
                    disabledTextColor = Color.Black.copy(alpha = 0.6f)
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
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color.Gray.copy(alpha = 0.1f)
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
                enabled = isEnabled,
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
                    unfocusedTextColor = Color.Black,
                    disabledBorderColor = Color.Gray.copy(alpha = 0.3f),
                    disabledTextColor = Color.Black.copy(alpha = 0.6f)
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