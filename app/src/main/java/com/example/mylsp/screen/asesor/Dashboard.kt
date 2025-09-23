package com.example.mylsp.screen.asesor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.model.api.assesment.Assessment
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.user.UserManager
import com.example.mylsp.viewmodel.AssesmentViewModel

@Composable
fun DashboardAsesor(
    modifier: Modifier = Modifier,
    assesmentViewModel: AssesmentViewModel,
    navController: NavController
) {
    val listAssesment by assesmentViewModel.listAssessment.collectAsState()
    val userManager = UserManager(LocalContext.current.applicationContext)
    val username = userManager.getUserName()
    val Blue = Color(0xFF1DA1F2)
    val LightGray = Color(0xFFF3F4F6)
    val Orange = Color(0xFFF7931E)
    val TextGray = Color(0xFF8A8A8A)
    val ButtonBlue = Color(0xFF2196F3)

    LaunchedEffect(Unit) {
        assesmentViewModel.getListAssesmentByUser(userManager.getUserId()?.toInt()?:0)
    }

    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        // HEADER
        HeaderArea(
            blue = Blue,
            lightGray = LightGray,
            modifier = Modifier.align(Alignment.TopStart)
        )

        // LIST KARTU
        CardsRow(
            modifier = Modifier
                .padding(top = 145.dp)
                .fillMaxWidth(),
            listAssesment,
            clickAssesment = {
                navController.navigate(Screen.DetailEvent.createRoute(it))
            }
        )

        // SAPAAN, STATISTIK, MENU, DAN TOMBOL
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 260.dp, bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Halo Asesor ${username?: "Who?"}",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF6B6B6B),
                    fontFamily = AppFont.Poppins
                )
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Selamat Datang di myLSP",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextGray,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AppFont.Poppins
                )
            )
            Spacer(Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(label = "Jumlah Asesi", value = "7609", orange = Orange, textGray = TextGray)
                StatItem(label = "Skema", value = "7609", orange = Orange, textGray = TextGray)
            }

            Spacer(Modifier.height(28.dp))

            // MENU TITLE
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Menu",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    fontFamily = AppFont.Poppins
                )
            }

            Spacer(Modifier.height(16.dp))

            MenuButton("Jadwal Sertifikasi", ButtonBlue, onClick = {
                navController.navigate(Screen.AssessmentList.route)
            })
            Spacer(Modifier.height(12.dp))
            MenuButton("Penilaian Asesi", ButtonBlue, onClick = {})
        }

        // BOTTOM NAV PIL

    }
}

@Composable
private fun HeaderArea(blue: Color, lightGray: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(190.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(blue)
        ) {
            // Ornamen garis
            Canvas(modifier = Modifier.fillMaxSize().padding(end = 14.dp, top = 6.dp)) {
                val origin = Offset(size.width * 0.74f, size.height * 0.18f)
                val unit = 54f
                val stroke = Stroke(width = 2.8f, cap = StrokeCap.Round)
                val c = Color.White.copy(alpha = 0.35f)
                for (i in 0..5) {
                    val o = origin + Offset(x = (i - 2) * (unit * 0.28f), y = i * (unit * 0.16f))
                    drawLine(c, start = o, end = o + Offset(unit, unit * 0.1f), strokeWidth = stroke.width, cap = stroke.cap)
                    drawLine(c, start = o, end = o + Offset(unit * 0.2f, unit * 0.9f), strokeWidth = stroke.width, cap = stroke.cap)
                    drawLine(c, start = o + Offset(unit * 0.2f, unit * 0.9f), end = o + Offset(unit, unit * 0.1f), strokeWidth = stroke.width, cap = stroke.cap)
                }
            }
        }

        // Logo kapsul kiri atas
        Row(
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp, bottomStart = 18.dp))
                .background(Color.White)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logoheader),
                modifier = Modifier.height(80.dp).padding(6.dp),
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "myLSP",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6B6B6B),
                    fontFamily = AppFont.Poppins
                )
            )
        }

        // Avatar kanan atas
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(58.dp)
                .clip(CircleShape)
                .background(Color(0xFFB07A3F))
        )
    }
}

private data class InfoCard(
    val title: String,
    val subtitle: String,
    val statusText: String,
    val statusType: StatusType
)

enum class StatusType { Running, Pending }

@Composable
private fun CardsRow(modifier: Modifier = Modifier, listAssesment: List<Assessment>, clickAssesment: (Int) -> Unit) {


    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listAssesment) { item -> SmallInfoCard(item, onClick = {clickAssesment(it)}) }
    }
}

@Composable
private fun SmallInfoCard(data: Assessment, onClick: (Int) -> Unit) {
    val bg = Color.White
    val stroke = Color(0xFFE5E7EB)
    val running = Color(0xFFBDE6FF)
    val pending = Color(0xFFEFEFEF)
    val textDark = Color(0xFF595959)
    val textLight = Color(0xFF9CA3AF)

    Surface(
        onClick = {
            onClick(data.id)
        },
        modifier = Modifier.width(240.dp).height(86.dp),
        color = bg,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(stroke))
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 10.dp)) {
            Text(
                text = data.schema.judul_skema,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = textDark,
                    fontFamily = AppFont.Poppins
                )
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = data.schema.nomor_skema,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textLight,
                        fontFamily = AppFont.Poppins
                    )
                )
                Spacer(Modifier.weight(1f))
                val pillColor = if (data.status.lowercase() == "active") running else pending
                val pillTextColor = if (data.status.lowercase() == "active") Color(0xFF4F9BD4) else Color(0xFF8C8C8C)
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(pillColor)
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.status,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = pillTextColor,
                            fontFamily = AppFont.Poppins
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, orange: Color, textGray: Color) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = textGray,
                fontFamily = AppFont.Poppins
            )
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = orange,
                fontFamily = AppFont.Poppins
            )
        )
    }
}

@Composable
private fun MenuButton(text: String, color: Color, onClick:()-> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick()},
        color = color
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins
            )
            Text(
                text = "➔",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = AppFont.Poppins
            )
        }
    }
}