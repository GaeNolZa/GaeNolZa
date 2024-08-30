package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gaenolza.R

data class Hotel(
    val id: Int,
    val name: String,
    val rating: Float,
    val reviewCount: Int,
    val price: Int,
    val imageResId: Int
)

@Composable
fun HotelScreen(navController: NavController) {
    val dummyHotels = remember {
        listOf(
            Hotel(1, "개랜드 호텔", 4.5f, 1234, 150000, R.drawable.hotel1),
            Hotel(2, "시티 독 호텔", 4.2f, 867, 120000, R.drawable.hotel2),
            Hotel(3, "오션 파라도기스", 4.7f, 2345, 200000, R.drawable.hotel3)
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                "추천 호텔",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dummyHotels.take(3)) { hotel ->
                    FeaturedHotelCard(hotel, onHotelClick = {
                        navController.navigate("hotelDetail/${hotel.id}")
                    })
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "모든 호텔",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(dummyHotels) { hotel ->
            HotelCard(hotel, onHotelClick = {
                navController.navigate("hotelDetail/${hotel.id}")
            })
        }
    }
}

@Composable
fun FeaturedHotelCard(hotel: Hotel, onHotelClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(200.dp)
            .clickable(onClick = onHotelClick)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = hotel.imageResId),
                contentDescription = "Hotel Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    hotel.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("★ ${hotel.rating}", color = Color.White)
                        Text("리뷰 ${hotel.reviewCount}개", color = Color.White)
                    }
                    Text(
                        "₩${hotel.price}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun HotelCard(hotel: Hotel, onHotelClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onHotelClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = hotel.imageResId),
                contentDescription = "Hotel Image",
                modifier = Modifier
                    .size(88.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(hotel.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("★ ${hotel.rating} (${hotel.reviewCount})")
            }
            Text(
                "₩${hotel.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}