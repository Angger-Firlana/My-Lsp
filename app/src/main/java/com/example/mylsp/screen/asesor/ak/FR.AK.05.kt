package com.example.mylsp.screen.asesor.ak

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont

@Composable
fun FRAK05(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderForm(
            "FR.AK.05",
            "PERTANYAAN WAWANCARA"
        )

        SkemaSertifikasi(
            judulUnit = "Okupasi Junior Custom Made",
            kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
            TUK = "Sewaktu/Tempat Kerja/Mandiri",
            namaAsesor = null,
            namaAsesi = null,
            tanggalAsesmen = null
        )

        Spacer(modifier = Modifier.height(8.dp))

        UnitKompetensi(
            kodeUnit = "I.555HDROOO.001.2",
            judulUnit = "Memproses Reservasi"
        )

        Spacer(modifier = Modifier.height(8.dp))

        InstruksiWawancara()

        PertanyaanWawancara()

        Spacer(modifier = Modifier.height(16.dp))

        FormNamaAsesiAsesor(
            namaAsesi = "Fawaz Muhammad Sena Rizki Ramadhan Lebaran Idul Adha",
            namaAsesor = "Fawaz Muhammad Sena Rizki Ramadhan"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TandaTanganAsesi(signerImage = null)

        Spacer(modifier = Modifier.height(16.dp))

        KirimJawabanButton { navController.navigate(Screen.Congrats.route) }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun UnitKompetensi(
    kodeUnit: String,
    judulUnit: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Unit Kompetensi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins
            )

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Column {
                    Text("Kode Unit", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                    Text("Judul Unit", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text( ":", fontSize = 12.sp, fontFamily = AppFont.Poppins )
                    Text( ":", fontSize = 12.sp, fontFamily = AppFont.Poppins )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text( kodeUnit, fontSize = 12.sp, fontFamily = AppFont.Poppins )
                    Text( judulUnit, fontSize = 12.sp, fontFamily = AppFont.Poppins )
                }
            }
        }
    }
}

@Composable
fun InstruksiWawancara() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Setiap pertanyaan harus diisi terkait dengan Elemen",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFont.Poppins
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Tuliskan bukti-bukti yang terdapat pada Ceklis Verifikasi Portofolio yang memerlukan wawancara",
            fontSize = 12.sp,
            fontFamily = AppFont.Poppins
        )
    }
}

@Composable
fun PertanyaanWawancara(
    modifier: Modifier = Modifier,
    onItemChecked: (Int, Boolean?) -> Unit = { _, _ -> },
) {
    val pertanyaanList = listOf(
        "Sesuai dengan bukti",
        "Sesuai dengan bukti",
        "Sesuai dengan bukti"
    )

    val checkboxStates = remember { mutableStateMapOf<Int, Boolean?>() }

    Column(modifier = modifier.fillMaxWidth()) {
        PertanyaanList(
            items = pertanyaanList,
            checkboxStates = checkboxStates,
            onItemChecked = onItemChecked
        )
    }
}

@Composable
fun PertanyaanList(
    items: List<String>,
    checkboxStates: MutableMap<Int, Boolean?>,
    onItemChecked: (Int, Boolean?) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Daftar Pertanyaan Wawancara",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEachIndexed { index, item ->
                PertanyaanItem(
                    index = index,
                    text = item,
                    isCheckedK = checkboxStates[index] == true,
                    isCheckedBK = checkboxStates[index] == false,
                    onKChecked = { checked ->
                        checkboxStates[index] = if (checked) true else null
                        onItemChecked(index, checkboxStates[index])
                    },
                    onBKChecked = { checked ->
                        checkboxStates[index] = if (checked) false else null
                        onItemChecked(index, checkboxStates[index])
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
fun PertanyaanItem(
    index: Int,
    text: String,
    isCheckedK: Boolean,
    isCheckedBK: Boolean,
    onKChecked: (Boolean) -> Unit,
    onBKChecked: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "${index + 1}. $text :",
            fontSize = 12.sp,
            fontFamily = AppFont.Poppins,
            lineHeight = 16.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckboxRekomendasiOption(
                label = "K",
                checked = isCheckedK,
                onCheckedChange = onKChecked
            )

            Spacer(modifier = Modifier.width(24.dp))

            CheckboxRekomendasiOption(
                label = "BK",
                checked = isCheckedBK,
                onCheckedChange = onBKChecked
            )
        }
    }
}

@Composable
fun CheckboxRekomendasiOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(18.dp),
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
fun FormNamaAsesiAsesor(
    namaAsesi: String?= null,
    namaAsesor: String?= null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nama Asesi",
                fontFamily = AppFont.Poppins,
                fontSize = 14.sp
            )

            OutlinedTextField(
                value = namaAsesi ?: "",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                textStyle = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nama Asesor",
                fontFamily = AppFont.Poppins,
                fontSize = 14.sp
            )

            OutlinedTextField(
                value = namaAsesor ?: "",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                textStyle = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TandaTanganAsesi(signerImage: Painter?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Tanda Tangan Asesi",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins
        )

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            signerImage?.let {
                Image(
                    painter = it,
                    contentDescription = "Tanda Tangan",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun KirimJawabanButton(onClick: () -> Unit) {
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