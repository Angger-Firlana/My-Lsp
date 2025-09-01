package com.example.mylsp.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mylsp.screen.BarcodeScreen
import com.example.mylsp.screen.CongratsScreen
import com.example.mylsp.screen.ProfileScreen
import com.example.mylsp.screen.asesi.APL02
import com.example.mylsp.screen.asesi.AsesiBarcodeScanner
import com.example.mylsp.screen.asesi.AsesiFormScreen
import com.example.mylsp.screen.asesi.DetailAssesment
import com.example.mylsp.screen.asesi.FRAK03
import com.example.mylsp.screen.asesor.FRAK01
import com.example.mylsp.screen.asesi.FRAK04
import com.example.mylsp.screen.asesor.FRAK05
import com.example.mylsp.screen.asesi.FRIA06A
import com.example.mylsp.screen.asesor.DashboardAsesor
import com.example.mylsp.screen.asesor.DetailEvent
import com.example.mylsp.screen.asesor.Events
import com.example.mylsp.screen.asesor.FRIA01
import com.example.mylsp.screen.asesor.KelengkapanDataAsesor
import com.example.mylsp.screen.asesor.AssesmentListScreen
import com.example.mylsp.screen.auth.LoginScreen
import com.example.mylsp.screen.auth.RegisterScreen
import com.example.mylsp.screen.main.MainScreen
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.util.UserManager
import com.example.mylsp.viewmodel.APL01ViewModel
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.SkemaViewModel
import com.example.mylsp.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(modifier: Modifier, userManager: UserManager, navController: NavHostController, startDestination: String, showBottomBar: (Boolean) -> Unit, showTopBar: (Boolean) -> Unit) {
    val context = LocalContext.current

    val skemaViewModel: SkemaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val assessmentViewModel: AssesmentViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val apL02ViewModel: APL02ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val asesiViewModel: AsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val userViewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val apl01ViewModel: APL01ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            showBottomBar(false)
            showTopBar(false)
            LoginScreen(
                userViewModel = userViewModel,
                successLogin = { role ->

                    when (role) {
                        "asesi" -> {
                            navController.navigate("main"){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        "asesor" -> {
                            navController.navigate("dashboardAsesor"){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        else -> {
                            navController.navigate("main"){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                    }
                },
                navigateRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable(Screen.Register.route) {
            showBottomBar(false)
            showTopBar(false)
            RegisterScreen(
                backToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Ia06a.route){
            FRIA06A(navController = navController)
        }
        composable(Screen.Ak05.route){
            FRAK05(navController = navController)
        }
        composable(Screen.KelengkapanDataAsesor.route) {
            KelengkapanDataAsesor(navController = navController)
        }
        composable(Screen.AssessmentList.route) {
            showTopBar(false)
            showBottomBar(true)
            val status = it.arguments?.getString("status")?: "pending"
            AssesmentListScreen(
                modifier = Modifier,
                assesmentViewModel = assessmentViewModel,
                navigateToWaitingScreen = {
                    navController.navigate(Screen.WaitingApproval.createRoute(status, Screen.AssessmentList.route)){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                },
                navigateToAssesment = {role, id ->
                    if(role == "assesi"){
                        navController.navigate(Screen.DetailAssesment.createRoute(id))
                    }else if (role == "assesor"){
                        navController.navigate(Screen.DetailEvent.route)
                    }
                },
                status = ""
            )
        }
        composable(Screen.DetailAssesment.route) {
            val id = it.arguments?.getString("id")?: "0"
            DetailAssesment(
                userManager = userManager,
                idAssessment = id.toInt(),
                onClickKerjakan = { idSkema ->
                    navController.navigate(Screen.Apl02.createRoute(idSkema))
                },
                apL01ViewModel = apl01ViewModel,
                assessmentViewModel = assessmentViewModel
            )
        }
        composable(Screen.Apl01.route) {
            showTopBar(true)
            showBottomBar(true)
            AsesiFormScreen(
                asesiViewModel,
                apl01ViewModel = apl01ViewModel,
                successSendingData = {
                    navController.navigate("main")
                },
                ifStatusPending = {status ->
                    navController.navigate(Screen.WaitingApproval.createRoute(status, Screen.Apl01.route)){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Apl02.route) {
            showTopBar(false)
            showBottomBar(false)
            val id = it.arguments?.getString("id")?: "0"
            APL02(
                id = id.toInt(),
                apL02ViewModel = apL02ViewModel,
                nextForm = {
                    navController.navigate(Screen.WaitingApproval.createRoute("accepted", "Apl02"))
                }
            )
        }
        composable(Screen.Ak01.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK01(nextForm = {
                navController.navigate(Screen.Ak04.route)
            })
        }
        composable(Screen.Ak03.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK03(navController = navController)
        }
        composable(Screen.Ak04.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK04(
                nextForm = {
                    navController.navigate(Screen.Ak03.route)
                }
            )
        }
        composable(Screen.Ak05.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK05(navController = navController)
        }
        composable(Screen.Ia01.route){
            showTopBar(false)
            showBottomBar(false)
            val id = it.arguments?.getString("id")?: "0"
            FRIA01(idSkema = id.toInt(),apL02ViewModel = apL02ViewModel,navController = navController)
        }
        composable(Screen.WaitingApproval.route) {
            showTopBar(false)
            showBottomBar(false)
            val route = it.arguments?.getString("route")?: "apl01"
            val status = it.arguments?.getString("status")?: "pending"
            WaitingApprovalScreen(
                modifier = Modifier,
                nextStep = {
                    if (route == Screen.Apl01.route){
                        navController.navigate(Screen.Apl01.route){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }else if(route == "Apl02"){
                        navController.navigate(Screen.Ak01.route)
                    }else{
                        navController.navigate(Screen.Main.route)
                    }
                },
                backFillForm = {
                    navController.navigate(Screen.Apl01.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                textButton = "Lanjut ke form selanjutnya",
                type = "jawaban",
                status = status
            )
        }

        composable(Screen.Congrats.route){
            showTopBar(false)
            showBottomBar(false)
            CongratsScreen(
                onNext = {
                    navController.navigate(Screen.Main.route)
                }
            )
        }
        composable(Screen.Profile.route){
            if (userManager.getUserRole() == "asesi"){
                showTopBar(true)
            }
            showBottomBar(false)
            ProfileScreen(modifier = Modifier, navController = navController)
        }
        composable(Screen.Main.route) {
            showTopBar(true)
            showBottomBar(true)
            MainScreen(modifier = Modifier, navController = navController)
        }
        composable(Screen.Events.route) {
            showBottomBar(false)
            Events(navController = navController)
        }
        composable(Screen.DashboardAsesor.route){
            showTopBar(true)
            showBottomBar(false)
            DashboardAsesor(navController = navController)
        }

        composable("scanningBarcode"){
            showBottomBar(false)
            showTopBar(false)
            AsesiBarcodeScanner(navController = navController)
        }
        composable("barcode"){
            showBottomBar(false)
            BarcodeScreen(text = "*&KJDHASD&^#!DASDHDAS")
        }
        composable(Screen.DetailEvent.route) {
            DetailEvent(navController = navController)
        }
    }
}