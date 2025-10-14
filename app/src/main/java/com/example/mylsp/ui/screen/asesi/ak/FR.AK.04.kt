package com.example.mylsp.ui.screen.asesi.ak

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.common.enums.TypeAlert
import com.example.mylsp.common.enums.TypeDialog
import com.example.mylsp.ui.component.alert.AlertCard
import com.example.mylsp.ui.component.form.HeaderForm
import com.example.mylsp.ui.component.form.SkemaSertifikasi
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.Ak04SubmissionManager
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.ui.component.MyLspButton
import com.example.mylsp.ui.component.dialog.StatusDialog
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel
import com.example.mylsp.viewmodel.assesment.ak.Ak04ViewModel

@Composable
fun FRAK04(
    modifier: Modifier = Modifier,
    viewModel: Ak04ViewModel,
    assesmentAsesiViewModel:AssesmentAsesiViewModel,
    nextForm: () -> Unit,
    backToDetailEvent:()->Unit
) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val role = userManager.getUserRole() ?: ""
    val manager = remember { Ak04SubmissionManager(context) }
    val assesmentAsesiManager = remember { AssesmentAsesiManager(context) }
    val stroke = Color(0xFFE6E6E6)
    val chipBg = Color(0xFFF1F3F4)

    val questionsState = viewModel.questions.collectAsState()
    val submissionsState = viewModel.submissions.collectAsState()
    val message = viewModel.message.collectAsState()
    val state by viewModel.state.collectAsState()
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()
    val assesmentAsesiId = assesmentAsesi?.id ?: 0

    // State untuk dialog
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // State untuk menyimpan jawaban dan alasan
    val answersState = remember { mutableStateMapOf<Int, String>() }
    var reasonText by remember { mutableStateOf(TextFieldValue("")) }

    // State untuk form sudah terisi atau belum
    var isFormFilled by remember { mutableStateOf(false) }
    var isFormEditable by remember { mutableStateOf(true) }

    // Validasi role - hanya asesi yang bisa mengisi
    val isAsesi = role.equals("asesi", ignoreCase = true) || role.equals("assesi", ignoreCase = true)

    val deleteState by assesmentAsesiViewModel.deleteState.collectAsState()
    var showSuccessDeleteDialog by remember { mutableStateOf(false) }
    var showFailedDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAk04Questions()
        viewModel.getAk04ByAsesi(assesmentAsesi?.id ?: 0)

        // Load saved answers dari local storage
        val savedAnswers = manager.getAllSubmissions(assesmentAsesiId)
        savedAnswers.forEach { submission ->
            answersState[submission.ak04_question_id] = submission.selected_option
        }

        val savedReason = manager.getReason(assesmentAsesiId)
        if (savedReason.isNotEmpty()) {
            reasonText = TextFieldValue(savedReason)
        }
    }

    LaunchedEffect(deleteState) {
        deleteState?.let {
            if (it) {
                showSuccessDeleteDialog = true
            } else {
                showFailedDeleteDialog = true
            }
        }
        assesmentAsesiViewModel.clearState()
    }

    // Cek apakah form sudah disubmit ke server
    LaunchedEffect(submissionsState.value) {
        val submissions = submissionsState.value
        if (submissions != null && submissions.isNotEmpty()) {
            isFormFilled = true
            isFormEditable = false

            // Load data dari server - ambil data pertama
            val serverData = submissions.firstOrNull()
            if (serverData != null) {
                // Load answers dari questions list
                serverData.questions.forEach { question ->
                    answersState[question.ak04_question_id] = question.selected_option
                }

                // Load reason dari alasan_banding
                if (serverData.alasan_banding.isNotEmpty()) {
                    reasonText = TextFieldValue(serverData.alasan_banding)
                }
            }
        }
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success) {
                showSuccessDialog = true
            } else {
                errorMessage = message.value
                showErrorDialog = true
            }
            viewModel.resetState()
        }
    }

    // Dialog Sukses
    if (showSuccessDialog) {
        StatusDialog(
            text = "Jawaban FR.AK.04 berhasil dikirim",
            type = TypeDialog.Success,
            onClick = {
                showSuccessDialog = false
                manager.clear(assesmentAsesiId)
                nextForm()
            },
            onDismiss = {
                showSuccessDialog = false
                manager.clear(assesmentAsesiId)
                nextForm()
            }
        )
    }

    // Dialog Gagal
    if (showErrorDialog) {
        StatusDialog(
            text = errorMessage,
            type = TypeDialog.Failed,
            onClick = {showErrorDialog = false},
            onDismiss = { showErrorDialog = false}
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderForm(title = "FR.AK.04", subTitle = "BANDING ASESMEN")

        // Alert jika bukan asesi
        if (!isAsesi) {
            AlertCard(
                message = "Hanya Asesi yang dapat mengisi formulir ini",
                type = TypeAlert.Warning
            )
            Spacer(Modifier.height(8.dp))
        }

        // Alert jika form sudah diisi
        if (isFormFilled) {
            AlertCard(
                message = "Formulir ini sudah diisi dan dikirim. Berikut adalah data yang telah Anda kirimkan.",
                type = TypeAlert.Info
            )
            Spacer(Modifier.height(8.dp))
        }

        SkemaSertifikasi(
        )

        Spacer(Modifier.height(8.dp))

        // Panel pertanyaan dinamis
        SectionCard(stroke) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(chipBg, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    "Jawablah dengan Ya atau Tidak pertanyaan-pertanyaan berikut ini :",
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            val qResponse = questionsState.value
            if (qResponse != null && qResponse.data.isNotEmpty()) {
                qResponse.data.forEachIndexed { index, q ->
                    val currentAnswer = answersState[q.id]

                    QuestionRow(
                        text = q.question,
                        yes = currentAnswer == "ya",
                        no = currentAnswer == "tidak",
                        onYes = {
                            if (isFormEditable && isAsesi) {
                                answersState[q.id] = "ya"
                                manager.saveAnswer(assesmentAsesiId, q.id, "ya")
                            }
                        },
                        onNo = {
                            if (isFormEditable && isAsesi) {
                                answersState[q.id] = "tidak"
                                manager.saveAnswer(assesmentAsesiId, q.id, "tidak")
                            }
                        },
                        divider = index != qResponse.data.lastIndex,
                        enabled = isFormEditable && isAsesi
                    )
                }
            } else {
                Text("Memuat pertanyaan...", fontFamily = AppFont.Poppins, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Alasan banding
        SectionCard(stroke) {
            Text(
                "Banding ini diajukan atas alasan berikut :",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            TextField(
                value = reasonText,
                onValueChange = { newValue ->
                    if (isFormEditable && isAsesi) {
                        reasonText = newValue
                        manager.saveReason(assesmentAsesiId, newValue.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                placeholder = {
                    Text(
                        if (isFormEditable && isAsesi) "Tulis alasan banding Anda di sini..."
                        else if (reasonText.text.isEmpty()) "Tidak ada alasan yang diisi"
                        else "",
                        fontFamily = AppFont.Poppins,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp
                ),
                enabled = isFormEditable && isAsesi,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (isFormEditable && isAsesi) Color(0xFFF3F4F6) else Color(0xFFE5E7EB),
                    unfocusedContainerColor = if (isFormEditable && isAsesi) Color(0xFFF3F4F6) else Color(0xFFE5E7EB),
                    disabledContainerColor = Color(0xFFE5E7EB),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Tombol Kirim - hanya tampil jika form belum terisi dan user adalah asesi
        if (isFormEditable && isAsesi) {
            Button(
                onClick = {
                    val qResponse = questionsState.value
                    if (qResponse != null) {
                        val allAnswered = qResponse.data.all { question ->
                            answersState.containsKey(question.id)
                        }

                        if (!allAnswered) {
                            Toast.makeText(
                                context,
                                "Harap jawab semua pertanyaan terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        if (reasonText.text.trim().isEmpty()) {
                            Toast.makeText(
                                context,
                                "Harap isi alasan banding terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                    }

                    // Build dan kirim request
                    val request = manager.buildRequest(assesmentAsesiId)
                    viewModel.sendSubmissionAk04(request)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                contentPadding = PaddingValues(vertical = 10.dp),
                enabled = questionsState.value != null
            ) {
                Text(
                    "Kirim Jawaban",
                    fontFamily = AppFont.Poppins,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        if(isFormFilled && !isAsesi){
            MyLspButton(
                onClick = {
                    assesmentAsesiViewModel.deleteAssesmentAsesi(assesmentAsesiId)
                },
                text = "Terima Banding",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }

    if (showSuccessDeleteDialog){
        StatusDialog(
            text = "Berhasil Menerima Banding, Asesi dapat mendaftar ulang pada assesment",
            type = TypeDialog.Success,
            onClick = {
                showSuccessDeleteDialog = false
                backToDetailEvent()
            },
            onDismiss = {
                showSuccessDialog = false
            }
        )
    }

    if (showFailedDeleteDialog){
        StatusDialog(
            text = "Gagal Menerima Banding, coba kembali",
            type = TypeDialog.Success,
            onClick = {
                showFailedDeleteDialog = false
                backToDetailEvent()
            },
            onDismiss = {
                showFailedDeleteDialog = false
            }
        )
    }
}

/* ==== Helper Components ==== */

@Composable
private fun SectionCard(stroke: Color, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 4.dp,
        border = BorderStroke(1.dp, stroke)
    ) {
        Column(Modifier.padding(12.dp), content = content)
    }
}

@Composable
private fun QuestionRow(
    text: String,
    yes: Boolean,
    no: Boolean,
    onYes: () -> Unit,
    onNo: () -> Unit,
    divider: Boolean,
    enabled: Boolean = true
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = text,
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                color = if (enabled) Color(0xFF111827) else Color(0xFF9CA3AF)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                // Checkbox Ya
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Ya",
                        fontFamily = AppFont.Poppins,
                        fontSize = 10.sp,
                        color = if (enabled) Color.Gray else Color(0xFFD1D5DB)
                    )
                    Checkbox(
                        checked = yes,
                        onCheckedChange = { if (it && enabled) onYes() },
                        enabled = enabled,
                        colors = CheckboxDefaults.colors(
                            checkedColor = if (enabled) MaterialTheme.colorScheme.tertiary else Color(0xFFD1D5DB),
                            uncheckedColor = if (enabled) Color.Gray else Color(0xFFD1D5DB),
                            disabledCheckedColor = Color(0xFFD1D5DB),
                            disabledUncheckedColor = Color(0xFFE5E7EB)
                        )
                    )
                }

                Spacer(Modifier.width(16.dp))

                // Checkbox Tidak
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Tidak",
                        fontFamily = AppFont.Poppins,
                        fontSize = 10.sp,
                        color = if (enabled) Color.Gray else Color(0xFFD1D5DB)
                    )
                    Checkbox(
                        checked = no,
                        onCheckedChange = { if (it && enabled) onNo() },
                        enabled = enabled,
                        colors = CheckboxDefaults.colors(
                            checkedColor = if (enabled) MaterialTheme.colorScheme.tertiary else Color(0xFFD1D5DB),
                            uncheckedColor = if (enabled) Color.Gray else Color(0xFFD1D5DB),
                            disabledCheckedColor = Color(0xFFD1D5DB),
                            disabledUncheckedColor = Color(0xFFE5E7EB)
                        )
                    )
                }
            }
        }

        if (divider) {
            HorizontalDivider(
                color = Color(0xFFE6E6E6),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}