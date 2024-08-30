package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.viewmodel.AnimalData
import com.example.gaenolza.viewmodel.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class SimpleDogInfo(
    val id: String,
    val name: String,
    val species: String,
    val birthDate: LocalDate,
    val gender: String,
    val imageResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogScreen(profileViewModel: ProfileViewModel,
              navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(profileViewModel.getAnimalInfo(1).animalName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sample_dog_image),
                contentDescription = "Dog Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoCard("종류", profileViewModel.getAnimalInfo(1).animalSpecies)
            Spacer(modifier = Modifier.height(8.dp))
            InfoCard("생일", profileViewModel.getAnimalInfo(1).animalBirthdate.toString())
            Spacer(modifier = Modifier.height(8.dp))
            InfoCard("성별", profileViewModel.getAnimalInfo(1).gender)
        }
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = value)
        }
    }
}
