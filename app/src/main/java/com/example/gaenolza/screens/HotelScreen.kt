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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gaenolza.Hotel
import com.example.gaenolza.ui.theme.ColorPalette


@Composable
fun HotelScreen(
    navController: NavController,
    hotels: List<Hotel> // dummyHotels 대신 hotels로 변경
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),

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
                items(hotels.take(3)) { hotel -> // hotels 리스트에서 상위 3개 항목 사용
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

        items(hotels) { hotel -> // hotels 리스트 사용
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
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column{
                        HotelStar(hotel, color = Color.White)
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
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    HotelStar(hotel)
                    Text(
                        "(${hotel.reviewCount})",
                        style = TextStyle(fontSize = 14.sp, color = Color.Black),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Text(
                "₩${hotel.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HotelStar(
    hotel: Hotel,
    fontSize: Int = 14,
    color: Color = Color.Black
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            "★", style = TextStyle(fontSize = fontSize.sp, color = ColorPalette.primaryPink),
            modifier = Modifier.padding(end = 2.dp)
        )
        Text("${hotel.rating}", style = TextStyle(fontSize = fontSize.sp, color = color))
    }
}