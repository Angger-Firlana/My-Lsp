package com.example.mylsp.screen.asesi

import androidx.compose.foundation.Image
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun APL01(modifier: Modifier = Modifier, navController: NavController) {
    var namaLengkap by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }
    var kebangsaan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenkel by remember { mutableStateOf("") }
    var fotoKartu by remember { mutableStateOf("") }
    var fotoDiri by remember { mutableStateOf("") }
    var judulSkema by remember { mutableStateOf("") }
    var nomorSkema by remember { mutableStateOf("") }
    var tujuanAssesment by remember { mutableStateOf("") }
    var fotRapot by remember { mutableStateOf("") }
    var suratKeteranganPKL by remember { mutableStateOf("") }

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
                .padding(16.dp),
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
                FieldLabel("Nama Lengkap")
                OutlinedTextField(
                    value = namaLengkap,
                    onValueChange = { namaLengkap = it },
                    placeholder = { FieldPlaceholder("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Tanggal Lahir")
                OutlinedTextField(
                    value = tanggalLahir,
                    onValueChange = { tanggalLahir = it },
                    placeholder = { FieldPlaceholder("dd/mm/yyyy") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Kelas")
                OutlinedTextField(
                    value = kelas,
                    onValueChange = { kelas = it },
                    placeholder = { FieldPlaceholder("Kelas") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Jurusan")
                OutlinedTextField(
                    value = jurusan,
                    onValueChange = { jurusan = it },
                    placeholder = { FieldPlaceholder("Jurusan") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Kebangsaan")
                OutlinedTextField(
                    value = kebangsaan,
                    onValueChange = { kebangsaan = it },
                    placeholder = { FieldPlaceholder("Kebangsaan") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

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

                FieldLabel("Foto Kartu Identitas")
                OutlinedTextField(
                    value = fotoKartu,
                    onValueChange = { fotoKartu = it },
                    placeholder = { FieldPlaceholder("Upload foto kartu identitas") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true
                )

                FieldLabel("Foto Diri")
                OutlinedTextField(
                    value = fotoDiri,
                    onValueChange = { fotoDiri = it },
                    placeholder = { FieldPlaceholder("Upload foto diri") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true
                )

                FieldLabel("Judul Skema Sertifikasi")
                OutlinedTextField(
                    value = judulSkema,
                    onValueChange = { judulSkema = it },
                    placeholder = { FieldPlaceholder("Judul Skema Sertifikasi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Nomor Skema Sertifikasi")
                OutlinedTextField(
                    value = nomorSkema,
                    onValueChange = { nomorSkema = it },
                    placeholder = { FieldPlaceholder("Nomor Skema Sertifikasi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                FieldLabel("Tujuan Assessment")
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tujuanAssesment == "sertifikasi",
                            onClick = { tujuanAssesment = "sertifikasi" }
                        )
                        Text("Sertifikasi", fontFamily = AppFont.Poppins)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tujuanAssesment == "pkt",
                            onClick = { tujuanAssesment = "pkt" }
                        )
                        Text("Pengakuan Kompetensi Terkini (PKT)", fontFamily = AppFont.Poppins)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tujuanAssesment == "rpl",
                            onClick = { tujuanAssesment = "rpl" }
                        )
                        Text("Rekognisi Pembelajaran Lampau", fontFamily = AppFont.Poppins)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tujuanAssesment == "lainnya",
                            onClick = { tujuanAssesment = "lainnya" }
                        )
                        Text("Lainnya", fontFamily = AppFont.Poppins)
                    }
                }

                FieldLabel("Foto Raport")
                OutlinedTextField(
                    value = fotRapot,
                    onValueChange = { fotRapot = it },
                    placeholder = { FieldPlaceholder("Upload foto raport") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true
                )

                FieldLabel("Surat Keterangan PKL")
                OutlinedTextField(
                    value = suratKeteranganPKL,
                    onValueChange = { suratKeteranganPKL = it },
                    placeholder = { FieldPlaceholder("Upload surat keterangan PKL") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true
                )

                Spacer(Modifier.height(30.dp))

                Button(
                    onClick = { navController.navigate("apl_02") },
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
            .padding(top = 16.dp, bottom = 8.dp)
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