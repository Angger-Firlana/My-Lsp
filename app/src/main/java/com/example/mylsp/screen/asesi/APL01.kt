package com.example.mylsp.screen.asesi

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.model.api.AttachmentRequest
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.APL01ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

data class FileUpload(
    val name: String,
    val description: String,
    var file: MultipartBody.Part? = null,
    var fileName: String? = null
)

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
    )
}

@Composable
fun AsesiFormScreen(
    viewModel: AsesiViewModel,
    apl01ViewModel: APL01ViewModel,
    successSendingData: ()-> Unit,
    ifStatusPending: (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val message by viewModel.message.collectAsState()
    val asesi by apl01ViewModel.formData.collectAsState()
    val loading by apl01ViewModel.loading.collectAsState()

    var namaLengkap by remember { mutableStateOf("Budi Santoso") }
    var nik by remember { mutableStateOf("3201010101010001") }
    var tanggalLahir by remember { mutableStateOf("1998-05-05") }
    var tempatLahir by remember { mutableStateOf("Jakarta") }
    var jenisKelamin by remember { mutableStateOf("Laki-laki") }
    var kebangsaan by remember { mutableStateOf("Indonesia") }
    var alamatRumah by remember { mutableStateOf("Jl. Melati No. 10, Jakarta Barat") }
    var kodePos by remember { mutableStateOf("11510") }
    var noTeleponRumah by remember { mutableStateOf("0215551234") }
    var noTeleponKantor by remember { mutableStateOf("0215551234") }
    var noTelepon by remember { mutableStateOf("081234567890") }
    var email by remember { mutableStateOf("budi.santoso@example.com") }
    var kualifikasiPendidikan by remember { mutableStateOf("S1 Teknik Informatika") }
    var namaInstitusi by remember { mutableStateOf("Universitas Indonesia") }
    var jabatan by remember { mutableStateOf("Software Engineer") }
    var alamatKantor by remember { mutableStateOf("Jl. Sudirman No. 25, Jakarta Pusat") }
    var kodePosKantor by remember { mutableStateOf("10210") }
    var faxKantor by remember { mutableStateOf("0215554321") }
    var emailKantor by remember { mutableStateOf("budi.santoso@kantor.com") }

    var fileUploads by remember {
        mutableStateOf(
            listOf(
                FileUpload("KTP", "Upload kartu tanda penduduk yang masih berlaku"),
                FileUpload("KK", "Upload kartu keluarga atau KTP"),
                FileUpload("PasFoto3x4", "Pas foto 3x4 berwarna merah 2 lembar"),
                FileUpload("RaportSMK", "Raport SMK semester 1 s.d. 5"),
                FileUpload("SertifikatPKL", "Sertifikat Praktek Kerja Lapangan")
            )
        )
    }

    var currentUploadIndex by remember { mutableStateOf(-1) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (currentUploadIndex >= 0) {
                val file = uriToFile(it, context)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData(
                    "${fileUploads[currentUploadIndex].name.lowercase().replace(" ", "_")}[]",
                    file.name,
                    requestFile
                )

                fileUploads = fileUploads.toMutableList().apply {
                    this[currentUploadIndex] = this[currentUploadIndex].copy(
                        file = part,
                        fileName = file.name
                    )
                }
            }
        }
    }

    LaunchedEffect(loading) {
        loading?.let { loading ->
            if (loading){

            }else{

            }
        }
    }

    LaunchedEffect(Unit) {
        apl01ViewModel.fetchFormApl01Status()

    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success) {
                successSendingData()
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        loading?.let {
            if(it){
                LoadingScreen()
            }else{
                apl01ViewModel.clearState()
            }
        }?:run{
            asesi?.let { dataAsesi ->
                if (dataAsesi.status == "pending") {
                    ifStatusPending(dataAsesi.status)
                }else{
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Detail Asesi",
                                style = TextStyle(
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            DetailItem(label = "Nama Lengkap", value = dataAsesi.nama_lengkap)
                            DetailItem(label = "No KTP", value = dataAsesi.no_ktp)
                            DetailItem(label = "Tanggal Lahir", value = dataAsesi.tgl_lahir)
                            DetailItem(label = "Tempat Lahir", value = dataAsesi.tempat_lahir)
                            DetailItem(label = "Jenis Kelamin", value = dataAsesi.jenis_kelamin)
                            DetailItem(label = "Kebangsaan", value = dataAsesi.kebangsaan)
                            DetailItem(label = "Alamat Rumah", value = dataAsesi.alamat_rumah)
                            DetailItem(label = "Kode Pos", value = dataAsesi.kode_pos)
                            DetailItem(label = "No Telepon Rumah", value = dataAsesi.no_telepon_rumah)
                            DetailItem(label = "No Telepon Kantor", value = dataAsesi.no_telepon_kantor)
                            DetailItem(label = "No Telepon", value = dataAsesi.no_telepon)
                            DetailItem(label = "Email", value = dataAsesi.email)
                            DetailItem(label = "Kualifikasi Pendidikan", value = dataAsesi.kualifikasi_pendidikan)
                            DetailItem(label = "Nama Institusi", value = dataAsesi.nama_institusi)
                            DetailItem(label = "Jabatan", value = dataAsesi.jabatan)
                            DetailItem(label = "Alamat Kantor", value = dataAsesi.alamat_kantor)
                            DetailItem(label = "Kode Pos Kantor", value = dataAsesi.kode_pos_kantor)
                            DetailItem(label = "Fax Kantor", value = dataAsesi.fax_kantor)
                            DetailItem(label = "Email Kantor", value = dataAsesi.email_kantor)
                            DetailItem(label = "Status", value = dataAsesi.status)

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "User Detail",
                                style = TextStyle(
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            )
                            DetailItem(label = "Username", value = dataAsesi.user.username)
                            DetailItem(label = "Email User", value = dataAsesi.user.email)
                            DetailItem(label = "Role", value = dataAsesi.user.role)
                        }
                    }
                }
            } ?: run {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {
                    SectionHeader("Data Pribadi")
                    ModernTextField(value = namaLengkap, onValueChange = { namaLengkap = it }, placeholder = "Nama Lengkap", label = "Nama Lengkap")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = nik, onValueChange = { nik = it }, placeholder = "NIK", label = "NIK")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = tanggalLahir, onValueChange = { tanggalLahir = it }, placeholder = "16 Februari 2008", label = "Tanggal Lahir")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = tempatLahir, onValueChange = { tempatLahir = it }, placeholder = "Tempat Lahir", label = "Tempat Lahir")
                    Spacer(Modifier.height(24.dp))

                    Text("Jenis Kelamin", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(bottom = 12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        GenderOption("Laki-laki", jenisKelamin == "Laki-laki") { jenisKelamin = "Laki-laki" }
                        GenderOption("Perempuan", jenisKelamin == "Perempuan") { jenisKelamin = "Perempuan" }
                    }
                    Spacer(Modifier.height(16.dp))

                    ModernTextField(value = kebangsaan, onValueChange = { kebangsaan = it }, placeholder = "Kebangsaan", label = "Kebangsaan")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = alamatRumah, onValueChange = { alamatRumah = it }, placeholder = "Alamat lengkap tempat tinggal", label = "Alamat Rumah", minLines = 3)
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = kodePos, onValueChange = { kodePos = it }, placeholder = "Kode Pos", label = "Kode Pos")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = noTeleponRumah, onValueChange = { noTeleponRumah = it }, placeholder = "No Telepon Rumah", label = "No Telepon Rumah")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = noTelepon, onValueChange = { noTelepon = it }, placeholder = "No Telepon/HP", label = "No Telepon")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = email, onValueChange = { email = it }, placeholder = "Email", label = "Email")
                    Spacer(Modifier.height(24.dp))

                    SectionHeader("Data Pendidikan")
                    ModernTextField(value = kualifikasiPendidikan, onValueChange = { kualifikasiPendidikan = it }, placeholder = "Kualifikasi Pendidikan", label = "Kualifikasi Pendidikan")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = namaInstitusi, onValueChange = { namaInstitusi = it }, placeholder = "Nama Institusi/Sekolah", label = "Nama Institusi")
                    Spacer(Modifier.height(24.dp))

                    SectionHeader("Data Pekerjaan")
                    ModernTextField(value = jabatan, onValueChange = { jabatan = it }, placeholder = "Jabatan", label = "Jabatan")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = alamatKantor, onValueChange = { alamatKantor = it }, placeholder = "Alamat Kantor", label = "Alamat Kantor", minLines = 2)
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = kodePosKantor, onValueChange = { kodePosKantor = it }, placeholder = "Kode Pos Kantor", label = "Kode Pos Kantor")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = faxKantor, onValueChange = { faxKantor = it }, placeholder = "Fax Kantor", label = "Fax Kantor")
                    Spacer(Modifier.height(16.dp))
                    ModernTextField(value = emailKantor, onValueChange = { emailKantor = it }, placeholder = "Email Kantor", label = "Email Kantor")
                    Spacer(Modifier.height(32.dp))

                    Text("Dokumen Pendukung", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(bottom = 16.dp))
                    fileUploads.forEachIndexed { index, fileUpload ->
                        FileUploadCard(fileUpload) {
                            currentUploadIndex = index
                            launcher.launch("*/*")
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                    Spacer(Modifier.height(40.dp))
                    Button(
                        onClick = {
                            val attachments = fileUploads.mapNotNull { fu ->
                                fu.file?.let { filePart ->
                                    AttachmentRequest(
                                        file = filePart,
                                        description = fu.description.toRequestBody("text/plain".toMediaTypeOrNull())
                                    )
                                }
                            }
                            val request = AsesiRequest(
                                nama_lengkap = namaLengkap,
                                nik = nik,
                                tgl_lahir = tanggalLahir,
                                tempat_lahir = tempatLahir,
                                jenis_kelamin = jenisKelamin,
                                kebangsaan = kebangsaan,
                                alamat_rumah = alamatRumah,
                                kode_pos = kodePos,
                                no_telepon_rumah = noTeleponRumah,
                                no_telepon_kantor = noTeleponKantor,
                                no_telepon = noTelepon,
                                email = email,
                                kualifikasi_pendidikan = kualifikasiPendidikan,
                                nama_institusi = namaInstitusi,
                                jabatan = jabatan,
                                alamat_kantor = alamatKantor,
                                kode_pos_kantor = kodePosKantor,
                                fax_kantor = faxKantor,
                                email_kantor = emailKantor,
                                status = "pending",
                                attachments = attachments
                            )
                            viewModel.createDataAsesi(request)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("🔗 Simpan data", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(16.dp))
                    message.takeIf { it.isNotEmpty() }?.let {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (state == true) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = it,
                                color = if (state == true) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }

    }
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String? = null,
    minLines: Int = 1
) {
    Column {
        label?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp),
            minLines = minLines,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Composable
fun GenderOption(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
        Text(text = text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun FileUploadCard(fileUpload: FileUpload, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(
                    if (fileUpload.file != null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (fileUpload.file != null) Icons.Default.Check else Icons.Default.AttachFile,
                    contentDescription = null,
                    tint = if (fileUpload.file != null) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
            ) {
                Text(fileUpload.name, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                Text(
                    text = fileUpload.fileName ?: fileUpload.description,
                    fontSize = 12.sp,
                    color = if (fileUpload.file != null) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
            if (fileUpload.file != null) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove file",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp).clickable {
                        // TODO: remove file logic
                    }
                )
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = TextStyle(
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = value ?: "-",
            style = TextStyle(
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        )
    }
}

// Utility functions
fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return file
}

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            name = it.getString(nameIndex)
        }
    }
    return name
}
