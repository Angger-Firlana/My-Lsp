package com.example.mylsp.ui.screen.auth

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylsp.R
import com.example.mylsp.data.api.auth.RegisterRequest
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.AuthViewModel
import com.example.mylsp.viewmodel.JurusanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    backToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )
    val jurusanViewModel: JurusanViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    var username by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jurusanTitle by remember { mutableStateOf("") }
    var jurusanId by remember { mutableStateOf(0) }
    var dropJurusans by remember { mutableStateOf(false) }

    val jurusans by jurusanViewModel.jurusans.collectAsState()
    val message by viewModel.message.collectAsState()

    val stateRegister by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(stateRegister) {
        stateRegister?.let { success->
            if (success){
                backToLogin()
            }else{
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        isLoading = false
        viewModel.resetState()
    }

    LaunchedEffect(jurusans) {
        Log.d("jurusan", "${jurusans.size}")
    }

    LaunchedEffect(Unit){
        jurusanViewModel.getJurusans()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selamat Datang",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(200.dp))

            Text(
                text = "Username",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("username", fontFamily = AppFont.Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                text = "Email",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("email", fontFamily = AppFont.Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )


            Text(
                text = "Password",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("password", fontFamily = AppFont.Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            ExposedDropdownMenuBox(
                expanded = dropJurusans,
                onExpandedChange = { dropJurusans = !dropJurusans }
            ) {
                OutlinedTextField(
                    value = jurusanTitle,
                    onValueChange = { },
                    readOnly = true,
                    label = {
                        Text(
                            text = "Select Jurusan",
                            fontFamily = AppFont.Poppins
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Choose your jurusan...",
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = if (dropJurusans) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (dropJurusans) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    textStyle = TextStyle(
                        fontFamily = AppFont.Poppins,
                        fontSize = 16.sp
                    )
                )

                ExposedDropdownMenu(
                    expanded = dropJurusans,
                    onDismissRequest = { dropJurusans = false },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    if (jurusans.isEmpty()) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "No jurusan available",
                                    fontFamily = AppFont.Poppins,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            },
                            onClick = { },
                            enabled = false
                        )
                    } else {
                        jurusans.forEach { jurusan ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = jurusan.nama_jurusan,
                                        fontFamily = AppFont.Poppins,
                                        fontSize = 16.sp,
                                        color = if (jurusanTitle == jurusan.nama_jurusan) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                },
                                onClick = {
                                    jurusanTitle = jurusan.nama_jurusan
                                    jurusanId = jurusan.id
                                    dropJurusans = false
                                },
                                leadingIcon = if (jurusanTitle == jurusan.nama_jurusan) {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                } else null,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    isLoading = true
                    viewModel.register(
                        RegisterRequest(
                            email, username, password, jurusanId
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Register",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sudah memiliki akun? ",
                    fontFamily = AppFont.Poppins
                )
                Text(
                    text = "klik disini!",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        backToLogin()
                    }
                )
            }
        }
    }
}
