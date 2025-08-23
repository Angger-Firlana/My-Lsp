package com.example.mylsp.screen.asesi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

/* FR.AK.04
 * - Gunakan komponen di /component: HeaderForm, SkemaSertifikasi, CheckboxHasilOption
 * - Bagian identitas: STATIC TEXT (tanpa input box) → mirip figma
 * - Checkbox pakai komponen existing (tanpa bikin komponen baru)
 * - Preview: 412 x 917
 */

@Composable
fun FRAK04(modifier: Modifier = Modifier) {
    val stroke = Color(0xFFE6E6E6)
    val chipBg = Color(0xFFF1F3F4)
    val labelColor = Color(0xFF4B5563) // abu label
    val valueColor = Color(0xFF111827) // teks utama

    // dummy data (static text — NO input boxes)
    val namaAsesor = "Yusma Yeni"
    val namaAsesi = "Rahma"
    val tanggalAsesmen = "19 Agustus 2025"

    val skemaSertifikasi = "Okupasi Junior Custom Made"
    val noSkema = "SKM.TBS.OJCM/LSP.SMKN24/2023"

    // state YA/TIDAK
    val checks = remember {
        mutableStateMapOf(
            0 to true,   // contoh terisi
            1 to false,
            2 to false
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header pakai komponen
        HeaderForm(
            title = "FR.AK.04",
            subTitle = "BANDING ASESMEN"
        )

        // Bagian Skema Sertifikasi atas (sudah static text via komponen)
        SkemaSertifikasi(
            judulUnit = skemaSertifikasi,
            kodeUnit = noSkema,
            TUK = "Sewaktu/Tempat Kerja/Mandiri",
            namaAsesor = namaAsesor,
            namaAsesi = namaAsesi,
            tanggalAsesmen = tanggalAsesmen
        )

        Spacer(Modifier.height(8.dp))

        // Panel identitas (STATIC TEXT — TANPA BOX)
        SectionCard(stroke) {
            Text("Identitas", fontFamily = AppFont.Poppins, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            StaticLabelValue("Nama Asesor", namaAsesor, labelColor, valueColor)
            StaticLabelValue("Nama Asesi", namaAsesi, labelColor, valueColor)
            StaticLabelValue("Tanggal Asesmen", tanggalAsesmen, labelColor, valueColor)
        }

        Spacer(Modifier.height(8.dp))

        // Pertanyaan (chip + list)
        SectionCard(stroke) {
            // Chip judul
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

            // Header kolom kanan
            Row(Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("YA", fontFamily = AppFont.Poppins, fontSize = 10.sp, color = labelColor)
                    Spacer(Modifier.width(24.dp))
                    Text("TIDAK", fontFamily = AppFont.Poppins, fontSize = 10.sp, color = labelColor)
                }
            }

            Spacer(Modifier.height(6.dp))

            QuestionRow(
                text = "Apakah proses branding telah dijelaskan kepada anda?",
                yes = checks[0] == true,
                no = checks[0] == false && checks.containsKey(0),
                onYes = { checks[0] = true },
                onNo = { checks[0] = false },
                divider = true
            )
            QuestionRow(
                text = "Apakah anda telah mendiskusikan banding dengan asesor",
                yes = checks[1] == true,
                no = checks[1] == false && checks.containsKey(1),
                onYes = { checks[1] = true },
                onNo = { checks[1] = false },
                divider = true
            )
            QuestionRow(
                text = "Apakah anda mau melibatkan \"orang lain\" membantu anda dalam proses banding",
                yes = checks[2] == true,
                no = checks[2] == false && checks.containsKey(2),
                onYes = { checks[2] = true },
                onNo = { checks[2] = false },
                divider = false
            )
        }

        Spacer(Modifier.height(8.dp))

        // Skema Sertifikasi (static label:value — TANPA BOX)
        SectionCard(stroke) {
            Text(
                "Banding ini diajukan atas keputusan asesmen yang dibuat terhadap skema sertifikasi (Kualifikasi/Klaster/Okupasi) berikut :",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))
            StaticLabelValue("Skema Sertifikasi", skemaSertifikasi, labelColor, valueColor)
            StaticLabelValue("No. Skema Sertifikasi", noSkema, labelColor, valueColor)
        }

        Spacer(Modifier.height(8.dp))

        // Alasan (placeholder abu-abu — bukan input)
        SectionCard(stroke) {
            Text("Banding ini diajukan atas alasan sebagai berikut :", fontFamily = AppFont.Poppins, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                color = Color(0xFFF3F4F6),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Box(Modifier.padding(12.dp)) {
                    Text(
                        "Tulis alasan…",
                        fontFamily = AppFont.Poppins,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            "Anda mempunyai hak mengajukan banding jika anda menilai proses asesmen tidak sesuai SOP dan tidak memenuhi prinsip asesmen.",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color(0xFF6B7280)
        )

        Spacer(Modifier.height(14.dp))

        // TTD dan Tanggal (static style)
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(1f)) {
                Text("Tanda tangan asesi :", fontFamily = AppFont.Poppins, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                SignatureBox()
                Spacer(Modifier.height(12.dp))
                SignatureBox()
            }
            Spacer(Modifier.width(20.dp))
            Column(Modifier.weight(1f)) {
                Text("Tanggal :", fontFamily = AppFont.Poppins, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                // Static text (tanpa box)
                StaticValueFrame(text = "dd-MM-yyyy")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Tombol bawah (pakai warna theme/tertiary yang sudah dipakai project)
        Button(
            onClick = { /* no-op preview */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            Text("Kirim Jawaban", fontFamily = AppFont.Poppins, fontSize = 16.sp, color = Color.White)
        }

        Spacer(Modifier.height(16.dp))
    }
}

/* ==== Helpers (inline, tanpa bikin file baru) ==== */

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
private fun StaticLabelValue(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontFamily = AppFont.Poppins, fontSize = 12.sp, color = labelColor, modifier = Modifier.width(140.dp))
        Text(":", fontFamily = AppFont.Poppins, fontSize = 12.sp, color = labelColor, modifier = Modifier.width(10.dp))
        Text(value, fontFamily = AppFont.Poppins, fontSize = 12.sp, color = valueColor)
    }
}

@Composable
private fun QuestionRow(
    text: String,
    yes: Boolean,
    no: Boolean,
    onYes: (Boolean) -> Unit,
    onNo: (Boolean) -> Unit,
    divider: Boolean
) {
    Column(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text, fontFamily = AppFont.Poppins, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CheckboxHasilOption(label = "", checked = yes, onCheckedChange = { onYes(true) })
                Spacer(Modifier.width(24.dp))
                CheckboxHasilOption(label = "", checked = no, onCheckedChange = { onNo(true) })
            }
        }
        if (divider) {
            Divider(color = Color(0xFFE6E6E6), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
private fun SignatureBox() {
    Box(
        Modifier
            .size(96.dp)
            .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(6.dp))
    )
}

@Composable
private fun StaticValueFrame(text: String) {
    // hanya untuk menempatkan teks tanpa box input; garis tipis bawah biar mirip figma
    Column(Modifier.fillMaxWidth()) {
        Text(text, fontFamily = AppFont.Poppins, fontSize = 12.sp, color = Color(0xFF6B7280))
        Spacer(Modifier.height(6.dp))
        Divider(color = Color(0xFFE6E6E6), thickness = 1.dp)
    }
}

/* ==== Preview 412 x 917 ==== */
@Preview(
    name = "FR.AK.04 – 412x917",
    showBackground = true,
    widthDp = 412,
    heightDp = 917
)
@Composable
fun FRAK04Preview() {
    MaterialTheme { FRAK04() }
}
