package com.example.mylsp.screen.main

import android.content.ClipData.Item
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.Util
import com.example.mylsp.viewmodel.UserViewModel

data class ItemBar(
    val icon: ImageVector,
    val title: String,
    val route: String
)

data class ItemBanner(
    val image: Int,
    val description: String,
)

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: UserViewModel = viewModel()
    val idUser = Util.logUser

    val banners = listOf(
        ItemBanner(R.drawable.banner1, "Selamat Datang Di MyLsp"),
        ItemBanner(R.drawable.banner2, "Deskripsi Banner 2")
    )
    val pagerState = rememberPagerState { banners.size }
    var currentBanner by remember { mutableStateOf(0) }

    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().height(200.dp).padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                HorizontalPager(state = pagerState) { page ->
                    currentBanner = page
                    val item = banners[page]
                    Box(Modifier.fillMaxSize()){
                        Image(
                            painter = painterResource(id = item.image),
                            contentDescription = item.description,
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        ) {

                        }
                        Text(
                            text = item.description,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.align(Alignment.Center)

                        )
                    }
                }
            }


            LazyRow {
                items(banners.size){
                    val checkCurrent = it == currentBanner
                   Card(
                       modifier = Modifier.height(10.dp).width(if(checkCurrent) 40.dp else 20.dp).padding(horizontal = 4.dp),
                       colors = CardDefaults.cardColors(
                           containerColor = if (checkCurrent) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(0.3f)
                       )
                   ) {

                   }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


