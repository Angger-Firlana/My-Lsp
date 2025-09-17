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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

@Composable
fun FRAK02(modifier: Modifier = Modifier, navController: NavController) {
    var isKompeten by remember { mutableStateOf(false) }
    var isBelumKompeten by remember { mutableStateOf(false) }
    var tindakLanjutText by remember { mutableStateOf("") }
    var observasiText by remember { mutableStateOf("") }

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

        UnitKompetensi(
            unitKompetensiList = sampleUnitKompetensiData
        )

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
    }
}

@Composable
fun UnitKompetensi(
    unitKompetensiList: List<UnitKompetensiItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        unitKompetensiList.forEach { item ->
            UnitKompetensiCard(item = item)
        }
    }
}

@Composable
fun UnitKompetensiCard(
    item: UnitKompetensiItem,
    modifier: Modifier = Modifier
) {
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
                text = item.title,
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
                items(item.options) { option ->
                    CheckboxOption(
                        option = option,
                        isChecked = option.isChecked,
                        onCheckedChange = { /* Handle checkbox change */ }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                "Geser Kesamping untuk melihat opsi yang lain",
                fontSize = 10.sp,
                fontFamily = AppFont.Poppins
            )
        }
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
            MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onCheckedChange(!isChecked) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.size(16.dp),
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = option.text,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = if (isChecked) MaterialTheme.colorScheme.onPrimaryContainer
                else Color.Black,
                lineHeight = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
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

val sampleUnitKompetensiData = listOf(
    UnitKompetensiItem(
        title = "Memberikan layanan secara prima kepada pelanggan.",
        options = standardKompetensiOptions
    ),
    UnitKompetensiItem(
        title = "Melakukan pekerjaan dalam lingkungan sosial yang beragam",
        options = standardKompetensiOptions
    ),
    UnitKompetensiItem(
        title = "Mengikuti prosedur kesehatan, keselamatan dan keamanan dalam bekerja",
        options = standardKompetensiOptions
    ),
    UnitKompetensiItem(
        title = "Memelihara Alat Jahit",
        options = standardKompetensiOptions
    ),
    UnitKompetensiItem(
        title = "Memelihara Alat Jahit",
        options = standardKompetensiOptions
    )
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
                modifier = Modifier.weight(1f)
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
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kompeten",
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
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
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Belum Kompeten",
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
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