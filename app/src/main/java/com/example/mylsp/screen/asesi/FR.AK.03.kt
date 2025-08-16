package com.example.mylsp.screen.asesi

import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

@Composable
fun FRAK03(modifier: Modifier = Modifier, navController: NavController) {
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
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Komponen(navController = navController)
    }
}


@Composable
fun Komponen(
    modifier: Modifier = Modifier,
    onItemChecked: (Int, Boolean?) -> Unit = { _, _ -> },
    navController: NavController
) {
    val komponenList = listOf(
        "Saya mendapatkan penjelasan yang cukup memadai mengenai proses asesmen/uji kompetensi",
        "Saya diberikan kesempatan untuk mempelajari standar kompetensi yang akan diujikan dan menilai diri sendiri terhadap standar tersebut",
        "Asesor memberikan kesempatan untuk mendiskusikan mengapa metoda, instrumen dan sumber asesmen serta jadwal asesmen",
        "Asesor berhasaha menggali seluruh bukti pendukung yang sesuai dengan kebutuhan pelatihan dan pengalaman yang saya miliki",
        "Saya sepenuhnya diberikan kesempatan untuk mendemonstrasikan kompetensi yang saya miliki selama asesmen",
        "Saya mendapatkan penjelasan yang memadai mengenai keputusan asesmen",
        "Asesor memberikan umpan balik yang mendukung setelah asesmen serta tidak lanjutnya",
        "Asesor bersama saya mempelajari semua dokumen asesmen serta menandatanganinya",
        "Saya mendapatkan jaminan kerahasiaan hasil asesmen serta penjelasannya mengenai dokumen asesmen",
        "Asesor menggunakan keterampilan komunikasi yang efektif selama asesmen"
    )

    val checkboxStates = remember { mutableStateMapOf<Int, Boolean?>() }
    var catatan by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        KomponenList(
            items = komponenList,
            checkboxStates = checkboxStates,
            onItemChecked = onItemChecked
        )

        Spacer(modifier = Modifier.height(16.dp))

        CatatanField(
            value = catatan,
            onValueChange = { catatan = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        KirimButton(
            onClick = { navController.navigate("ak05") }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun KomponenList(
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
                text = "Komponen",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEachIndexed { index, item ->
                KomponenItem(
                    text = item,
                    isCheckedYes = checkboxStates[index] == true,
                    isCheckedNo = checkboxStates[index] == false,
                    onYesChecked = { checked ->
                        checkboxStates[index] = if (checked) true else null
                        onItemChecked(index, checkboxStates[index])
                    },
                    onNoChecked = { checked ->
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
fun KomponenItem(
    text: String,
    isCheckedYes: Boolean,
    isCheckedNo: Boolean,
    onYesChecked: (Boolean) -> Unit,
    onNoChecked: (Boolean) -> Unit
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
                text = text,
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
                checked = isCheckedYes,
                onCheckedChange = onYesChecked
            )

            Spacer(modifier = Modifier.width(24.dp))

            CheckboxHasilOption(
                label = "Tidak",
                checked = isCheckedNo,
                onCheckedChange = onNoChecked
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
            .height(200.dp),
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