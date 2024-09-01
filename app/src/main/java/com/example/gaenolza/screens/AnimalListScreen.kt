package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.dataclass.Reservation
import com.example.gaenolza.network.sendGetAnimalsByCustomerId
import com.example.gaenolza.network.sendGetFacilityById
import com.example.gaenolza.network.sendGetReservations
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.ui.theme.GaeNolZaTheme
import com.example.gaenolza.viewmodel.Facility
import com.example.gaenolza.viewmodel.ProfileViewModel
import com.example.gaenolza.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun AnimalListScreen(
    profileViewModel: ProfileViewModel,
    reservationViewModel: ReservationViewModel,
    navController: NavController
) {
    val animalListState = profileViewModel.animalDataState.collectAsState().value

    LaunchedEffect(Unit) {
        sendGetAnimalsByCustomerId(7) { result ->
            result.fold(
                onSuccess = { fetchedAnimals ->
                    profileViewModel.updateAnimalDataState(fetchedAnimals)
                },
                onFailure = { error ->
                    println("Error fetching animals: ${error.message}")
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
        AnimalListTopbar()
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(animalListState) { animalList ->
                AnimalListCard(
                    animalName = animalList.animalName,
                    animalSpecies = animalList.animalSpecies,
                    animalBirthdate = animalList.animalBirthdate,
                    animalGender = animalList.gender,
                    imageResource = R.drawable.sample_dog_image,
                    animalId = animalList.animalId,
                    reservationViewModel = reservationViewModel,
                    navController
                )
            }
        }
    }
}

@Composable
fun AnimalListTopbar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(ColorPalette.primaryPink),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "반려동물 목록",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AnimalListCard(
    animalName: String,
    animalSpecies: String,
    animalBirthdate: LocalDate,
    animalGender: String,
    imageResource: Int,
    animalId: Int,
    reservationViewModel: ReservationViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit){
                detectTapGestures {
                    reservationViewModel.updatePathAnimalId(animalId)
                    navController.popBackStack()
                }
            },
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
                    text = animalName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = animalSpecies,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF336699), // Adjust color as per your theme
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = animalBirthdate.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}
