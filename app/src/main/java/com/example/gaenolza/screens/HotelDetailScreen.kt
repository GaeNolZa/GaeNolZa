package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gaenolza.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailScreen(navController: NavController, hotelId: Int) {
    val dummyHotels = remember {
        listOf(
            Hotel(1, "개랜드 호텔", 4.5f, 1234, 150000, R.drawable.hotel1),
            Hotel(2, "시티 독 호텔", 4.2f, 867, 120000, R.drawable.hotel2),
            Hotel(3, "오션 파라도기스", 4.7f, 2345, 200000, R.drawable.hotel3)
        )
    }

    val hotel = dummyHotels.find { it.id == hotelId } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(hotel.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = hotel.imageResId),
                contentDescription = "Hotel Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    hotel.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("★ ${hotel.rating}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("(${hotel.reviewCount} 리뷰)", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("가격: ₩${hotel.price}/박", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Text("호텔 설명", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("이 호텔에 대한 자세한 설명을 여기에 추가하세요. 위치, 편의시설, 서비스 등의 정보를 포함할 수 있습니다.")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* 예약 로직 추가 */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("예약하기")
                }
            }
        }
    }
}