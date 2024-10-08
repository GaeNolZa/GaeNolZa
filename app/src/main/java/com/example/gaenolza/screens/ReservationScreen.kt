package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gaenolza.R
import com.example.gaenolza.network.sendGetReservations
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.viewmodel.HotelViewModel
import com.example.gaenolza.viewmodel.ReservationViewModel

@Composable
fun ReservationScreen(
    reservationViewModel: ReservationViewModel,
    hotelViewModel: HotelViewModel,
    customerId: Int
) {
    val reservationDataState = reservationViewModel.reservationDateState.collectAsState().value

    LaunchedEffect(Unit) {
        sendGetReservations(customerId) { result ->
            result.fold(
                onSuccess = { fetchedReservations ->
                    reservationViewModel.updateReservationDataList(fetchedReservations)
                },
                onFailure = { error ->
                    println("Error fetching reservations: ${error.message}")
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ReservationTopBar()
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(reservationDataState) { reservation ->
                val facility = hotelViewModel.getHotelInfoByID(reservation.facilityId)
                val title = facility.name
                val location = facility.address ?: "Loading..."

                ReservationCard(
                    date = reservation.reservationDate.toString(),
                    title = title,
                    location = location,
                    imageResource = facility.imageResId // Replace with actual image resource
                )
            }
        }
    }
}

@Composable
fun ReservationTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(ColorPalette.primaryPink),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "예약내역",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ReservationCard(
    date: String,
    title: String,
    location: String,
    imageResource: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF336699), // Adjust color as per your theme
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}