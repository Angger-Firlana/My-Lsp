package com.example.mylsp.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.R
import com.example.mylsp.component.BottomPillNav
import com.example.mylsp.component.TopAppBar
import com.example.mylsp.screen.BarcodeScreen
import com.example.mylsp.screen.ProfileScreen
import com.example.mylsp.screen.asesi.APL02
import com.example.mylsp.screen.asesi.AsesiBarcodeScanner
import com.example.mylsp.screen.asesi.AsesiFormScreen
import com.example.mylsp.screen.asesi.DetailUSK
import com.example.mylsp.screen.asesi.FRAK05
import com.example.mylsp.screen.asesi.FRIA06A
import com.example.mylsp.screen.asesor.DashboardAsesor
import com.example.mylsp.screen.asesor.DetailEvent
import com.example.mylsp.screen.asesor.Events
import com.example.mylsp.screen.asesor.FRIA01
import com.example.mylsp.screen.asesor.KelengkapanDataAsesor
import com.example.mylsp.screen.asesor.SignatureScreen
import com.example.mylsp.screen.asesor.SkemaListScreen
import com.example.mylsp.screen.auth.LoginScreen
import com.example.mylsp.screen.auth.RegisterScreen
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.screen.main.MainScreen
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.UserManager
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import com.example.mylsp.viewmodel.SkemaViewModel
import com.example.mylsp.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val startDestination = if (UserManager(context).getUserId() != null){
        if (UserManager(context).getUserRole() == "asesor"){
            "dashboardAsesor"
        }else{
            "main"
        }
    }else{
        "login"
    }
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        ItemBar(Icons.Default.QrCode, "Barcode", "barcode"),
        ItemBar(Icons.Default.AccountCircle, "Profil", "profile"),
    )
    val skemaViewModel:SkemaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val apL02ViewModel:APL02ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val asesiViewModel: AsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val userViewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val routesWithNavigation = listOf("main", "skemaList", "profil")
    var showNavigation by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar()
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxWidth().padding(innerPadding)){
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable("login") {
                    showNavigation = false
                    showTopBar = false
                    LoginScreen(userViewModel = userViewModel, navController = navController)
                }
                composable("register") {
                    showNavigation = false
                    showTopBar = false
                    RegisterScreen(navController = navController)
                }

                composable("apl05"){
                    FRAK05(navController = navController)
                }
                composable("kelengkapanDataAsesor") {
                    KelengkapanDataAsesor(navController = navController)
                }
                composable("tanda_tangan_asesor") {
                    SignatureScreen(context, navController)
                }
                composable("skemaList") {
                    showTopBar = false
                    showNavigation = true
                    SkemaListScreen(modifier = Modifier, skemaViewModel = skemaViewModel, navController = navController)
                }
                composable("detailusk") {
                    DetailUSK(navController = navController, idSkema = 1)
                }
                composable("apl_01") {
                    showTopBar = true
                    showNavigation = true
                    AsesiFormScreen(asesiViewModel,navController = navController)
                }
                composable("apl02/{id}") {
                    showTopBar = false
                    showNavigation = false
                    val id = it.arguments?.getString("id")?: "0"
                    APL02(id = id.toInt(),apL02ViewModel = apL02ViewModel,navController = navController)
                }
                composable("ia01/{id}"){
                    showTopBar = false
                    showNavigation = false
                    val id = it.arguments?.getString("id")?: "0"
                    FRIA01(idSkema = id.toInt(),apL02ViewModel = apL02ViewModel,navController = navController)
                }
                composable("waiting_approval") {
                    showTopBar = false
                    showNavigation = false
                    WaitingApprovalScreen(modifier = Modifier, navController)
                }
                composable("profile"){
                    if (userManager.getUserRole() == "asesi"){
                        showTopBar = true
                    }
                    showNavigation = true
                    ProfileScreen(modifier = Modifier, navController = navController)
                }
                composable("main") {
                    showTopBar = true
                    showNavigation = true
                    MainScreen(modifier = Modifier, navController = navController)
                }
                composable("events") {
                    showNavigation = false
                    Events(navController = navController)
                }
                composable("dashboardAsesor"){
                    showTopBar = false
                    showNavigation = true
                    DashboardAsesor(navController = navController)
                }

                composable("scanningBarcode"){
                    showNavigation = false
                    showTopBar = false
                    AsesiBarcodeScanner(navController = navController)
                }
                composable("barcode"){
                    showNavigation = false
                    BarcodeScreen(text = "*&KJDHASD&^#!DASDHDAS")
                }
                composable("detail_event") {
                    DetailEvent(navController = navController)
                }
            }

            if (showNavigation) {
                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomPillNav(
                        bottomBar = bottomNavItems,
                        orange = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 18.dp),
                        navController = navController
                    )
                }
            }
        }

    }
}




