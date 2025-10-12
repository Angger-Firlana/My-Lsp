package com.example.mylsp.ui.screen.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.model.api.Apl01
import com.example.mylsp.model.api.Attachment
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.user.AsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.viewmodel.assesment.apl.APL01ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ItemBar(
    val icon: ImageVector,
    val title: String,
    val route: String
)

data class ItemBanner(
    val image: Int,
    val description: String,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    apl01ViewModel: APL01ViewModel,
    aK05ViewModel: AK05ViewModel,
    asesiViewModel: AsesiViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val apl01Data by apl01ViewModel.formData.collectAsState()
    val asesi by asesiViewModel.asesi.collectAsState()
    val asesiManager = AsesiManager(context)
    val AK05 by aK05ViewModel.submission.collectAsState()
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val userManager = UserManager(context)

    val banners = listOf(
        ItemBanner(R.drawable.banner1, "Selamat Datang Di MyLsp"),
        ItemBanner(R.drawable.banner2, "Deskripsi Banner 2"),
        ItemBanner(R.drawable.senaaska, "Testing")
    )
    val pagerState = rememberPagerState { banners.size }
    var currentBanner by remember { mutableStateOf(0) }
    val assesmentAsesi by assesmentAsesiViewModel.assesmentAsesi.collectAsState()

    // Pull to Refresh State
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Function to refresh data
    fun refreshData() {
        coroutineScope.launch {
            isRefreshing = true
            try {
                asesiViewModel.getDataAsesiByUser(userManager.getUserId()?.toInt() ?: 0)
                aK05ViewModel.getSubmission(assesmentAsesi?.id?: 0)
                apl01ViewModel.fetchFormApl01Status()
                assesmentAsesiViewModel.getAssesmentAsesiByAsesi(asesi?.id ?: 0)

                // Add delay to show refresh animation
                delay(500)

                assesmentAsesi?.let { assesmentAsesi ->
                    assesmentAsesiManager.setAssesmentAsesiId(assesmentAsesi.id)
                    assesmentAsesiManager.saveAssesmentAsesi(assesmentAsesi)
                    Log.d("assesmentAsesiMainScreenInLetBody", assesmentAsesi.toString())
                }
                Log.d("assesmentAsesiMainScreen", assesmentAsesi.toString())
            } finally {
                isRefreshing = false
            }
        }
    }

    // Initial data load
    LaunchedEffect(Unit) {
        asesiViewModel.getDataAsesiByUser(userManager.getUserId()?.toInt() ?: "0".toInt())
        aK05ViewModel.getSubmission(asesiManager.getId())
        Log.d("testAsesiid", asesiManager.getId().toString())
        apl01ViewModel.fetchFormApl01Status()
        assesmentAsesiViewModel.getAssesmentAsesiByAsesi(asesi?.id?: 0)
        assesmentAsesi?.let { assesmentAsesi ->
            assesmentAsesiManager.setAssesmentAsesiId(assesmentAsesi.id)
            assesmentAsesiManager.saveAssesmentAsesi(assesmentAsesi)
            Log.d("assesmentAsesiMainScreenInLetBody", assesmentAsesi.toString())
        }
        Log.d("assesmentAsesiMainScreen", assesmentAsesi.toString())
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { refreshData() }
    )

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Box(Modifier.fillMaxSize().padding(bottom = 64.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Enhanced Banner Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    HorizontalPager(state = pagerState) { page ->
                        currentBanner = page
                        val item = banners[page]
                        Box(Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = item.image),
                                contentDescription = item.description,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Gradient overlay for better text readability
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.4f),
                                                Color.Black.copy(alpha = 0.7f)
                                            )
                                        )
                                    )
                            )

                            Text(
                                text = item.description,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(20.dp)
                            )
                        }
                    }
                }

                // Enhanced Banner Indicators
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(banners.size) { index ->
                        val isSelected = index == currentBanner
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .size(
                                    width = if (isSelected) 24.dp else 8.dp,
                                    height = 8.dp
                                )
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Enhanced Action Buttons
                if (assesmentAsesi == null) {
                    ElevatedButton(
                        onClick = { navController.navigate(Screen.AssessmentList.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 4.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewList,
                            contentDescription = "List Assessment",
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            "Pilih Skema Assessment",
                            fontFamily = AppFont.Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    ElevatedButton(
                        onClick = { navController.navigate(Screen.ListFormScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 4.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        enabled = assesmentAsesi?.status?.lowercase() != "kompeten",
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NavigateNext,
                            contentDescription = "Continue Assessment",
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            if(assesmentAsesi?.status?.lowercase() == "kompeten") "Assesment Sudah Berakhir" else "Lanjutkan Assessment",
                            fontFamily = AppFont.Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Enhanced APL01 Section
                EnhancedApl01Card(
                    apl01Data = apl01Data,
                    onNavigateToApl01 = { navController.navigate(Screen.Apl01.route) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        EnhancedInfoRow(
                            Icons.Default.Assessment,
                            label = "Hasil Assesment",
                            value = if (AK05?.data?.get(0)?.keputusan.isNullOrEmpty()) "Belum ada keputusan" else if(AK05?.data?.get(0)?.keputusan == "k") "Kompeten" else "Belum Kompeten"
                        )
                    }
                }
            }
        }

        // Pull to Refresh Indicator
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedApl01Card(
    apl01Data: Apl01?,
    onNavigateToApl01: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        apl01Data?.let { apl01 ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "APL01 Data",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Data APL01",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Formulir Permohonan Sertifikasi",
                            fontFamily = AppFont.Poppins,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Status chip
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                "Lengkap",
                                fontSize = 12.sp,
                                fontFamily = AppFont.Poppins
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Personal Information Section
                SectionCard(
                    title = "Informasi Pribadi",
                    icon = Icons.Default.PersonOutline
                ) {
                    EnhancedInfoRow(
                        icon = Icons.Default.Badge,
                        label = "Nama Lengkap",
                        value = apl01Data.nama_lengkap ?: "-"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = apl01.email ?: "-"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.Phone,
                        label = "No. Telepon",
                        value = apl01.no_telepon ?: "-"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.Home,
                        label = "Alamat Rumah",
                        value = apl01.alamat_rumah ?: "-"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Education & Professional Section
                SectionCard(
                    title = "Pendidikan & Profesi",
                    icon = Icons.Default.School
                ) {
                    EnhancedInfoRow(
                        icon = Icons.Default.Cake,
                        label = "Tempat, Tgl Lahir",
                        value = "${apl01Data.tempat_lahir ?: "-"}, ${apl01.tgl_lahir ?: "-"}"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.School,
                        label = "Kualifikasi Pendidikan",
                        value = apl01Data.kualifikasi_pendidikan ?: "-"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.Business,
                        label = "Institusi",
                        value = apl01Data.nama_institusi ?: "-"
                    )
                    EnhancedInfoRow(
                        icon = Icons.Default.Work,
                        label = "Jabatan",
                        value = apl01Data.jabatan ?: "-"
                    )
                }

                // Enhanced Attachments Section
                if (apl01.attachments != null && apl01.attachments.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    SectionCard(
                        title = "Dokumen Lampiran",
                        icon = Icons.Default.AttachFile
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 4.dp)
                        ) {
                            items(apl01.attachments) { attachment ->
                                AttachmentCard(
                                    attachment = attachment,
                                    onClick = {
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(attachment.viewUrl))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            Log.e("AttachmentClick", "Error opening attachment: ${e.message}")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } ?: run {
            // Empty state for APL01
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Empty state illustration
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = "Empty Form",
                        tint = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Form APL01 Belum Diisi",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Silakan lengkapi formulir permohonan sertifikasi kompetensi untuk melanjutkan proses assessment",
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                FilledTonalButton(
                    onClick = onNavigateToApl01,
                    colors = ButtonDefaults.buttonColors(
                      containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Isi Form APL01",
                        fontFamily = AppFont.Poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentCard(
    attachment: Attachment,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // File icon container
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getFileIcon(attachment),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // File description
            Text(
                text = attachment.description ?: "Dokumen Lampiran",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
        }
    }
}

// Helper functions for attachment handling
fun getFileIcon(attachment: Any): ImageVector {
    return Icons.Default.InsertDriveFile
}

@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun EnhancedInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontFamily = AppFont.Poppins,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}