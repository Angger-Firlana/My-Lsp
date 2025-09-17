package com.example.mylsp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.component.ItemProfile
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.UserDetail
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.user.TokenManager
import com.example.mylsp.util.user.UserManager
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val tokenManager = TokenManager(context)

    var showLogout by remember { mutableStateOf(false) }
    var logout by remember { mutableStateOf(false) }



    val user = UserDetail(
        userManager.getUserId()?.toInt() ?:0,
        userManager.getUserName()?: "Unknown",
        userManager.getUserEmail()?: "Unknown",
        userManager.getUserRole()?: "Unknown",
        userManager.getJurusanId()
    )

    Box(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.size(140.dp),
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
                        .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                        .border(3.dp, MaterialTheme.colorScheme.background, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Info Section
            Text(
                text = user.username,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = user.email,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Role Badge
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = user.role.uppercase(),
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(3) { index ->
                    val (title, value) = when (index) {
                        0 -> Pair("Projects", "12")
                        1 -> Pair("Tasks", "48")
                        else -> Pair("Completed", "36")
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = value,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = title,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Quick Actions
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Quick Actions",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )



                val actions = listOf(
                    ItemProfile("Data Diri Anda (APL01)",Icons.Default.ExitToApp, Color(0xFFF44336), "apl_01"),
                    ItemProfile("Edit Profil", Icons.Default.Edit, Color(0xFF4CAF50), "editProfil"),
                    ItemProfile("Setelan",Icons.Default.Settings, Color(0xFF2196F3), "setting"),
                    ItemProfile("Bantuan dan Support", Icons.Default.Help, Color(0xFFFF9800), "helpSupport"),
                    ItemProfile("Logout",Icons.Default.ExitToApp, Color(0xFFF44336), "logout")

                )

                actions.forEach { (title, icon, color, route) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                when (route) {
                                    "logout" -> {
                                        showLogout = true
                                    }

                                    else -> navController.navigate(route)
                                }
                            }
                            .padding(16.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = color,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = title,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if(showLogout){
            DialogLogout(
                onDismissRequest = {
                    showLogout = false
                },
                onConfirm = {

                    logout = true

                }
            )
        }

        if (logout){
            LoadingScreen()
            LaunchedEffect(Unit) {
                delay(2000)
                logout = false
                userManager.clearUser()
                tokenManager.clearToken()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogLogout(
    onDismissRequest:()->Unit,
    onConfirm:()->Unit
) {
    AlertDialog(
        title = { Text("Konfirmasi Logout", fontFamily = AppFont.Poppins, fontWeight = FontWeight.Bold) },
        text = { Text("Apakah kamu yakin ingin keluar dari akun ini?",  fontFamily = AppFont.Poppins) },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )
            ) {
                Text("Ya", fontFamily = AppFont.Poppins)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Batal", fontFamily = AppFont.Poppins)
            }
        }
    )
}