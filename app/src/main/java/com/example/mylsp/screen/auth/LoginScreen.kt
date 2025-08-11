package com.example.mylsp.screen.auth

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.LoginRequest
import com.example.mylsp.repository.AuthRepository
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current.applicationContext
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    var username by remember { mutableStateOf("anjai") }
    var password by remember { mutableStateOf("123456") }

    val stateLogin by viewModel.state.collectAsState()
    val message by viewModel.message.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(stateLogin) {
        stateLogin?.let { success ->
            isLoading = false
            if (success) {
                navController.navigate("main"){
                    popUpTo(navController.graph.startDestinationId){inclusive = true}
                }
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background image di bawah
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
            // Judul
            Text(
                text = "Selamat Datang",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(200.dp))

            // Username
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
                placeholder = {
                    Text(
                        "username",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Normal
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
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
                placeholder = {
                    Text(
                        "password",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Normal
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    isLoading = true
                    viewModel.login(
                        LoginRequest(
                            input = username,
                            password = password
                    ))

                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
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
                    text = "Tidak punya akun? ",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "klik disini!",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }
        }

        if(isLoading){
            LoadingScreen()
        }
    }
}

