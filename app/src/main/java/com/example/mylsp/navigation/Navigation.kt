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
import com.example.mylsp.screen.BarcodeScreen
import com.example.mylsp.screen.ProfileScreen
import com.example.mylsp.screen.asesi.APL02
import com.example.mylsp.screen.asesi.AsesiFormScreen
import com.example.mylsp.screen.asesi.DetailUSK
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
        ItemBar(Icons.Default.Dashboard, "Dashboard", "main"),
        ItemBar(Icons.AutoMirrored.Filled.ViewList, "List Skema", "skemaList"),
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

    val routesWithNavigation = listOf("main", "skemaList", "profil")
    var showNavigation by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar()
            }
        },
        bottomBar = {
            if (showNavigation) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomPillNav(
                        orange = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 18.dp),
                        navController = navController
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                showNavigation = false
                showTopBar = false
                LoginScreen(navController = navController)
            }
            composable("register") {
                showNavigation = false
                showTopBar = false
                RegisterScreen(navController = navController)
            }
            composable("kelengkapanDataAsesor") {
                KelengkapanDataAsesor(navController = navController)
            }
            composable("tanda_tangan_asesor") {
                SignatureScreen(context, navController)
            }
            composable("skemaList") {
                showNavigation = true
                SkemaListScreen(modifier = Modifier, skemaViewModel = skemaViewModel, navController = navController)
            }
            composable("detailusk") {
                DetailUSK(navController = navController, idSkema = 1)
            }
            composable("apl_01") {
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
                showNavigation = true
                DashboardAsesor(navController = navController)
            }
            composable("barcode"){
                showNavigation = false
                BarcodeScreen(text = "*&KJDHASD&^#!DASDHDAS")
            }
            composable("detail_event") {
                DetailEvent(navController = navController)
            }
        }
    }
}

@Composable
private fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(MaterialTheme.colorScheme.tertiary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
        )

        Text(
            text = "MyLSP",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        IconButton(
            onClick = {
                // Handle menu action
            }
        ) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun BottomPillNav(orange: Color, modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    Surface(
        modifier = modifier.width(320.dp).height(54.dp),
        color = orange,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavIcon(Icons.Default.Apps, onClick = {
                if(UserManager(context).getUserRole() == "asesor"){
                    navController.navigate("dashboardAsesor")

                }else{
                    navController.navigate("main")

                }
            })
            NavIcon(Icons.Default.QrCodeScanner, onClick = {navController.navigate("barcode")})
            NavIcon(Icons.Default.Person, onClick = {navController.navigate("profile")})
        }
    }
}

@Composable
private fun NavIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }

}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    items: List<ItemBar>,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}