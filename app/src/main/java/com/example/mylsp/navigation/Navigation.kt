package com.example.mylsp.navigation

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.BottomPillNav
import com.example.mylsp.component.TopAppBar
import com.example.mylsp.screen.BarcodeScreen
import com.example.mylsp.screen.ProfileScreen
import com.example.mylsp.screen.asesi.*
import com.example.mylsp.screen.asesor.*
import com.example.mylsp.screen.auth.LoginScreen
import com.example.mylsp.screen.auth.RegisterScreen
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.screen.main.MainScreen
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.FormApl01Manager
import com.example.mylsp.util.TokenManager
import com.example.mylsp.util.UserManager
import com.example.mylsp.viewmodel.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val tokenManager = TokenManager(context)

    val userId = userManager.getUserId()
    val userRole = userManager.getUserRole()
    val token = tokenManager.getToken() ?: ""

    val navController = rememberNavController()

    val formViewModel: APL01ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            context.applicationContext as Application
        )
    )
    val skemaViewModel: SkemaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )
    val apL02ViewModel: APL02ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )
    val asesiViewModel: AsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )
    val userViewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val formData by formViewModel.formData.collectAsState()
    val status = formData?.status

    // Fetch APL01 status
    LaunchedEffect(Unit) {
        if (userId != null && userRole == "asesi" && token.isNotEmpty()) {
            formViewModel.fetchFormApl01Status()
        }
    }

    // Debug log
    LaunchedEffect(formData) {
        Log.d("AppNavigation", "Status: ${formData?.status}")
        Log.d("AppNavigation", "Token: $token")
    }

    // Tentukan startDestination
    val startDestination = when {
        userId == null -> "login"
        userRole == "asesor" -> "dashboardAsesor"
        status == null -> "apl_01"
        else -> "waiting_approval"
    }

    val bottomNavItems = listOf(
        ItemBar(Icons.Default.QrCode, "Barcode", "barcode"),
        ItemBar(Icons.Default.AccountCircle, "Profil", "profile"),
    )

    Scaffold(
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf("apl_01", "profile", "main", "skemaList")) TopAppBar()
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {

            NavHost(navController = navController, startDestination = startDestination) {

                // Auth
                composable("login") { LoginScreen(userViewModel = userViewModel, navController = navController) }
                composable("register") { RegisterScreen(navController = navController) }

                // APL Forms
                composable("apl_01") { AsesiFormScreen(asesiViewModel, navController) }
                composable("apl02/{id}") {
                    val id = it.arguments?.getString("id")?.toIntOrNull() ?: 0
                    APL02(id = id, apL02ViewModel = apL02ViewModel, navController = navController)
                }
                composable("ia01/{id}") {
                    val id = it.arguments?.getString("id")?.toIntOrNull() ?: 0
                    FRIA01(idSkema = id, apL02ViewModel = apL02ViewModel, navController = navController)
                }
                composable("apl06") { FRIA06A(navController = navController) }
                composable("apl05") { FRAK05(navController = navController) }

                // Waiting & Main
                composable("waiting_approval") {
                    WaitingApprovalScreen(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        status = formData?.status
                    )
                }
                composable("main") { MainScreen(modifier = Modifier, navController = navController) }

                // Profile
                composable("profile") { ProfileScreen(modifier = Modifier, navController = navController) }

                // Skema
                composable("skemaList") {
                    SkemaListScreen(
                        modifier = Modifier,
                        skemaViewModel = skemaViewModel,
                        navController = navController,
                        status = formData?.status
                    )
                }
                composable("detailusk") { DetailUSK(navController = navController, idSkema = 1) }

                // Asesor
                composable("dashboardAsesor") { DashboardAsesor(navController = navController) }
                composable("events") { Events(navController = navController) }
                composable("detail_event") { DetailEvent(navController = navController) }
                composable("kelengkapanDataAsesor") { KelengkapanDataAsesor(navController = navController) }
                composable("tanda_tangan_asesor") { SignatureScreen(context, navController) }

                // Barcode
                composable("scanningBarcode") { AsesiBarcodeScanner(navController = navController) }
                composable("barcode") { BarcodeScreen(text = "*&KJDHASD&^#!DASDHDAS") }

                // AK / FRAK
                composable("ak01") { FRAK01(navController = navController) }
            }

            // Bottom navigation
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf("profile", "main", "skemaList", "dashboardAsesor")) {
                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomPillNav(
                        bottomBar = bottomNavItems,
                        orange = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(bottom = 18.dp),
                        navController = navController
                    )
                }
            }
        }
    }
}