package com.example.mylsp.screen.asesor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun KelengkapanDataAsesor(modifier: Modifier = Modifier, navController: NavController) {
    var namaLengkap by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenkel by remember { mutableStateOf("") }
    var pekerjaan by remember { mutableStateOf("") }
    var ktp by remember { mutableStateOf("") }
    var ijazahTerakhir by remember { mutableStateOf("") }
    var sertifikatKompetensi by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }

    Box(modifier = Modifier) {
        Image(
            painter = painterResource(R.drawable.img),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Lengkapi identitas anda",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nama Lengkap
                FieldLabel("Nama Lengkap")
                OutlinedTextField(
                    value = namaLengkap,
                    onValueChange = { namaLengkap = it },
                    placeholder = { FieldPlaceholder("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // Tanggal Lahir
                FieldLabel("Tanggal Lahir")
                OutlinedTextField(
                    value = tanggalLahir,
                    onValueChange = { tanggalLahir = it },
                    placeholder = { FieldPlaceholder("Tanggal Lahir") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // Alamat
                FieldLabel("Alamat")
                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    placeholder = { FieldPlaceholder("Alamat") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(16.dp)
                )

                // Jenis Kelamin
                FieldLabel("Jenis Kelamin")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = jenkel == "pria",
                        onClick = { jenkel = "pria" }
                    )
                    Text("Pria", fontFamily = AppFont.Poppins)

                    Spacer(Modifier.width(16.dp))

                    RadioButton(
                        selected = jenkel == "wanita",
                        onClick = { jenkel = "wanita" }
                    )
                    Text("Wanita", fontFamily = AppFont.Poppins)
                }

                // Pekerjaan
                FieldLabel("Pekerjaan")
                OutlinedTextField(
                    value = pekerjaan,
                    onValueChange = { pekerjaan = it },
                    placeholder = { FieldPlaceholder("Pekerjaan") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // KTP
                FieldLabel("KTP")
                OutlinedTextField(
                    value = ktp,
                    onValueChange = { ktp = it },
                    placeholder = { FieldPlaceholder("Masukkan foto KTP anda") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // Ijazah Terakhir
                FieldLabel("Ijazah Terakhir")
                OutlinedTextField(
                    value = ijazahTerakhir,
                    onValueChange = { ijazahTerakhir = it },
                    placeholder = { FieldPlaceholder("Masukkan foto ijazah terakhir anda") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // Sertifikat Kompetensi
                FieldLabel("Sertifikat Kompetensi Keahlian")
                OutlinedTextField(
                    value = sertifikatKompetensi,
                    onValueChange = { sertifikatKompetensi = it },
                    placeholder = { FieldPlaceholder("Masukkan foto sertifikat kompetensi keahlian anda") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // Foto 3x4
                FieldLabel("Foto 3x4")
                OutlinedTextField(
                    value = foto,
                    onValueChange = { foto = it },
                    placeholder = { FieldPlaceholder("Masukkan foto 3x4 anda") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(Modifier.height(30.dp))

                // Save Button
                Button(
                    onClick = { navController.navigate("tanda_tangan_asesor") },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Simpan Data",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text,
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
}

@Composable
private fun FieldPlaceholder(text: String) {
    Text(
        text,
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
}
