package com.example.mylsp.screen.asesor.apl

import android.util.Log
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
import com.example.mylsp.model.api.Attachment
import com.example.mylsp.model.api.assesment.Apl02
import com.example.mylsp.model.api.assesment.ElemenAPL02
import com.example.mylsp.model.api.assesment.UnitApl02
import com.example.mylsp.model.api.assesment.GetAPL02Response
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.assesment.JawabanManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.util.user.UserManager
import com.example.mylsp.viewmodel.APL01ViewModel
import com.example.mylsp.viewmodel.APL02ViewModel

@Composable
fun APL02Check(
    modifier: Modifier = Modifier,
    id: Int,
    apl01ViewModel: APL01ViewModel,
    userManager: UserManager,
    apL02ViewModel: APL02ViewModel,
    nextForm: () -> Unit
) {
    val context = LocalContext.current
    val asesiManager = AsesiManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val jawabanManager = remember { JawabanManager() }
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val apl02Submission by apL02ViewModel.apl02Submission.collectAsState()
    val message by apL02ViewModel.message.collectAsState()
    val state by apL02ViewModel.state.collectAsState()
    val apl01Data by apl01ViewModel.formData.collectAsState()
    var isSubmitted by remember { mutableStateOf(false) }

    // Check if form is already submitted and can't be edited
    val isReadOnly = remember(apl02Submission) {
        apl02Submission?.let { submission ->
            // Form is read-only if it's already submitted and user is assesi
            userManager.getUserRole() == "assesi" && submission.data[0].details.isNotEmpty()
        } ?: false
    }

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(id)
        apL02ViewModel.getSubmissionByAsesi(asesiManager.getId())
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

                // Show submission status if exists
                apl02Submission?.let { submission ->
                    SubmissionStatusCard(submission, userManager.getUserRole()!!)
                }

                SchemaSection(data)
                InstructionCard()
                UnitsSection(assesmentAsesiId = assesmentAsesiManager.getAssesmentId(), data, listAttachment = apl01Data?.attachments!!, jawabanManager, apl02Submission, isReadOnly)

                if (userManager.getUserRole() == "assesi") {
                    if (!isReadOnly) {
                        SubmitButton(apL02ViewModel, nextForm, titleButton = "Kirim Jawaban")
                    }
                } else {
                    SubmitButton(apL02ViewModel, nextForm, titleButton = "Setuju", buttonUnApproved = "Tidak Setuju")
                }

                if (message.isNotEmpty()) {
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
private fun SubmissionStatusCard(
    submission: GetAPL02Response,
    userRole: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (userRole == "assesi")
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (userRole == "assesi") "Status: Sudah Disubmit" else "Menunggu Persetujuan",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tanggal Submit: ${submission.data[0].submission_date}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
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
    assesmentAsesiId: Int,
    data: Apl02,
    listAttachment: List<Attachment>,
    jawabanManager: JawabanManager,
    submission: GetAPL02Response?,
    isReadOnly: Boolean
) {
    data.data.units.forEach { unit ->
        UnitCompetensiSection(
            assesmentAsesiId = assesmentAsesiId,
            listAttachment = listAttachment,
            unit = unit,
            jawabanManager = jawabanManager,
            submission = submission,
            isReadOnly = isReadOnly
        )
    }
}

@Composable
private fun UnitCompetensiSection(
    assesmentAsesiId: Int,
    unit: UnitApl02,
    listAttachment: List<Attachment>,
    jawabanManager: JawabanManager,
    submission: GetAPL02Response?,
    isReadOnly: Boolean
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

    unit.elements.forEach { elemen ->
        // Find existing data for this element
        val existingDetail = submission?.data?.get(0)?.details?.find { detail ->
            detail.unit_ke == unit.unit_ke &&
                    detail.kode_unit == unit.kode_unit &&
                    detail.elemen_id == elemen.id
        }

        // Extract existing bukti (evidence) names
        val existingBuktiNames = existingDetail?.attachments?.map { attachment ->
            attachment.bukti.description
        } ?: emptyList()

        ElemenCard(
            elemen = elemen,
            buktiList = listAttachment,
            existingKompetensinitas = existingDetail?.kompetensinitas,
            existingBuktiNames = existingBuktiNames,
            isReadOnly = isReadOnly,
            onSubmit = { buktiRelevans, kompetensinitas ->
                if (!isReadOnly) {
                    jawabanManager.upsertElemen(
                        skemaId = assesmentAsesiId,
                        unitKe = unit.unit_ke,
                        kodeUnit = unit.kode_unit,
                        elemenId = elemen.elemen_index,
                        kompetensinitas = kompetensinitas,
                        buktiList = buktiRelevans
                    )
                }
            }
        )
    }
}

@Composable
private fun ElemenCard(
    elemen: ElemenAPL02,
    buktiList: List<Attachment>,
    existingKompetensinitas: String?,
    existingBuktiNames: List<String>,
    isReadOnly: Boolean,
    onSubmit: (List<String>, String) -> Unit
) {
    // Initialize with existing data or default values
    var selectedOption by remember { mutableStateOf(existingKompetensinitas ?: "") }
    val buktiSelected = remember { mutableStateListOf<String>() }

    // Initialize bukti selection with existing data
    LaunchedEffect(existingBuktiNames) {
        buktiSelected.clear()
        buktiSelected.addAll(existingBuktiNames)
    }

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
            containerColor = if (isReadOnly)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else
                MaterialTheme.colorScheme.background,
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

            elemen.kriteria_untuk_kerja.forEach { kuk ->
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
        isReadOnly = isReadOnly,
        onOptionSelected = { option ->
            selectedOption = option
            onSubmit(buktiSelected, selectedOption)
        }
    )

    // Bukti Relevan Section
    BuktiRelevanSection(
        buktiList = buktiList,
        selectedBukti = buktiSelected,
        isReadOnly = isReadOnly,
        onBuktiChanged = { bukti, isChecked ->
            if (isChecked) {
                buktiSelected.add(bukti)
            } else {
                buktiSelected.remove(bukti)
            }
            onSubmit(buktiSelected, selectedOption)
        }
    )

    // Show existing bukti that are not in the standard list
    val customBukti = existingBuktiNames.filter { it !in buktiRelevans }
    if (customBukti.isNotEmpty()) {
        ExistingCustomBuktiSection(customBukti)
    }
}

@Composable
private fun ExistingCustomBuktiSection(customBukti: List<String>) {
    Text(
        text = "Bukti Tambahan (dari submission sebelumnya)",
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
        customBukti.forEach { bukti ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = null,
                    enabled = false
                )
                Text(
                    text = bukti,
                    fontFamily = AppFont.Poppins,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    isReadOnly: Boolean,
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
                    onClick = { if (!isReadOnly) onOptionSelected(option) },
                    enabled = !isReadOnly
                )
                Text(
                    text = option.uppercase(),
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = if (isReadOnly) Color.Gray else Color.Black
                )
            }
        }
    }
}

@Composable
private fun BuktiRelevanSection(
    buktiList: List<Attachment>,
    selectedBukti: List<String>,
    isReadOnly: Boolean,
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
                isChecked = bukti.description in selectedBukti,
                isReadOnly = isReadOnly,
                onCheckedChange = { isChecked ->
                    onBuktiChanged(bukti.description, isChecked)
                }
            )
        }
    }
}

@Composable
private fun BuktiCheckboxItem(
    bukti: Attachment,
    isChecked: Boolean,
    isReadOnly: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .let { modifier ->
                if (!isReadOnly) {
                    modifier.clickable {
                        onCheckedChange(!isChecked)
                    }
                } else {
                    modifier
                }
            }
            .padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                if (!isReadOnly) onCheckedChange(checked)
            },
            enabled = !isReadOnly
        )
        Text(
            text = bukti.description,
            fontFamily = AppFont.Poppins,
            color = if (isReadOnly) Color.Gray else Color.Black,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun SubmitButton(
    apL02ViewModel: APL02ViewModel,
    nextForm: () -> Unit,
    titleButton: String = "Kirim Jawaban",
    buttonUnApproved: String? = null
) {
    Button(
        onClick = {
            if (buttonUnApproved == null) {
                val jawaban = com.example.mylsp.util.Util.jawabanApl02.value
                apL02ViewModel.sendApl02()
                Log.d("APL02", "Jawaban: $jawaban")

            }

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

    if (buttonUnApproved != null) {
        Button(
            onClick = {
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = buttonUnApproved,
                fontFamily = AppFont.Poppins
            )
        }
    }
}