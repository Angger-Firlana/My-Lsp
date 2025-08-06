package com.example.mylsp.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
<<<<<<< HEAD
import androidx.compose.runtime.*
=======
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
>>>>>>> origin/master
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
<<<<<<< HEAD
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.util.AppFont
=======
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylsp.R
>>>>>>> origin/master

@Composable
fun RegisterScreen(modifier: Modifier = Modifier, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
<<<<<<< HEAD
    var email by remember { mutableStateOf("") }

    Box(modifier = Modifier) {
        Image(
            painter = painterResource(R.drawable.img),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
=======
    var email by remember{ mutableStateOf("") }
    Box(modifier = Modifier){
        Image(
            painter = painterResource(R.drawable.img,),
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
>>>>>>> origin/master
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
<<<<<<< HEAD
            modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Selamat Datang",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(200.dp))

            // Username
            Text(
                "Username",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
=======
            modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Selamat Datang"
            )
            Spacer(Modifier.height(200.dp))
            Text(
                "Username",
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = username,
<<<<<<< HEAD
                onValueChange = { username = it },
                placeholder = {
                    Text(
                        "username",
                        fontFamily = AppFont.Poppins
                    )
                },
=======
                onValueChange = {
                    username = it
                },
                placeholder = { Text("username") },
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

<<<<<<< HEAD
            // Password
            Text(
                "Password",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
=======
            Text(
                "Password",
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
<<<<<<< HEAD
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        "password",
                        fontFamily = AppFont.Poppins
                    )
                },
=======
                onValueChange = {
                    password = it
                },
                placeholder = { Text("password") },
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

<<<<<<< HEAD
            // NIK
            Text(
                "NIK",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
=======
            Text(
                "NIK",
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nik,
<<<<<<< HEAD
                onValueChange = { nik = it },
                placeholder = {
                    Text(
                        "nik",
                        fontFamily = AppFont.Poppins
                    )
                },
=======
                onValueChange = {
                    nik = it
                },
                placeholder = { Text("password") },
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

<<<<<<< HEAD
            // Email
            Text(
                "Email",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
=======
            Text(
                "Email",
>>>>>>> origin/master
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
<<<<<<< HEAD
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        "email",
                        fontFamily = AppFont.Poppins
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // Register Button
            Button(
                onClick = { navController.navigate("login") },
=======
                onValueChange = {
                    email = it
                },
                placeholder = { Text("email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Button(
                onClick = {
                    navController.navigate("login")
                },
>>>>>>> origin/master
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
            ) {
                Text(
<<<<<<< HEAD
                    "Register",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium
                )
            }

            // Already have account
=======
                    "Register"
                )
            }

>>>>>>> origin/master
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
<<<<<<< HEAD
                    "Sudah memiliki akun? ",
                    fontFamily = AppFont.Poppins
                )
                Text(
                    "klik disini!",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
=======
                    "Sudah memiliki akun? "
                )
                Text(
                    "klik disini!",
>>>>>>> origin/master
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
<<<<<<< HEAD
    }
}
=======

    }
}
>>>>>>> origin/master
