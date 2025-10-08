package com.example.mylsp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.component.ItemProfile
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.UserDetail
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.assesment.IA01SubmissionManager
import com.example.mylsp.util.assesment.JawabanManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.util.user.TokenManager
import com.example.mylsp.util.user.UserManager
import com.example.mylsp.viewmodel.APL01ViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val tokenManager = TokenManager(context)
    val asesiManager = AsesiManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val jawabanManager = JawabanManager()
    val ia01SubmissionManager = IA01SubmissionManager(context)
    var showLogout by remember { mutableStateOf(false) }
    var logout by remember { mutableStateOf(false) }

    val user = UserDetail(
        userManager.getUserId()?.toInt() ?: 0,
        userManager.getUserName() ?: "Unknown",
        userManager.getUserEmail() ?: "Unknown",
        userManager.getUserRole() ?: "Unknown",
        userManager.getJurusanId()
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(750.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9966))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(0.dp))

                    Text(
                        text = user.username,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = user.email,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "VALID",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "48",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Asesmen yang diikuti",
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(
                                        color = Color(0xFFFF9966),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.role.uppercase(),
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Quick Actions",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
                        )

                        val actions = listOf(
                            ItemProfile(
                                "Data Diri Anda (APL01)",
                                Icons.Default.EditNote, MaterialTheme.colorScheme.tertiary,
                                Screen.Apl01.route
                            ),
                            ItemProfile(
                                "Logout", Icons.Default.ExitToApp,
                                MaterialTheme.colorScheme.tertiary, "logout"
                            )
                        )

                        actions.forEach { (title, icon, color, route) ->
                            if (userManager.getUserRole() == "assesor") {
                                if (route != Screen.Apl01.route) {
                                    ActionItem(
                                        title = title,
                                        icon = icon,
                                        iconColor = color,
                                        onClick = {
                                            when (route) {
                                                "logout" -> showLogout = true
                                                else -> navController.navigate(route)
                                            }
                                        }
                                    )
                                }
                            } else {
                                ActionItem(
                                    title = title,
                                    icon = icon,
                                    iconColor = color,
                                    onClick = {
                                        when (route) {
                                            "logout" -> showLogout = true
                                            else -> navController.navigate(route)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(110.dp)
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            val imageBitmap = ImageBitmap.imageResource(
                LocalContext.current.resources,
                R.drawable.senaaska
            )

            Image(
                painter = BitmapPainter(imageBitmap),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(3.dp, Color(0xFFFF9966), CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF10B981))
                    .border(3.dp, Color.White, CircleShape)
            )
        }

        if (showLogout) {
            DialogLogout(
                onDismissRequest = { showLogout = false },
                onConfirm = { logout = true }
            )
        }

        if (logout) {
            LoadingScreen()
            LaunchedEffect(Unit) {
                delay(2000)
                logout = false
                userManager.clearUser()
                tokenManager.clearToken()
                asesiManager.clear()
                assesmentAsesiManager.clear()

                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
private fun ActionItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF1F2937),
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogLogout(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                "Konfirmasi Logout",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        },
        text = {
            Text(
                "Apakah kamu yakin ingin keluar dari akun ini?",
                fontFamily = AppFont.Poppins,
                color = Color(0xFF6B7280)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Ya", fontFamily = AppFont.Poppins, fontWeight = FontWeight.Medium)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Batal", fontFamily = AppFont.Poppins, fontWeight = FontWeight.Medium)
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}