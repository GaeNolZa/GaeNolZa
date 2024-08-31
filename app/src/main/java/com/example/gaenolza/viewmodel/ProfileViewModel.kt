package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gaenolza.R
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

data class ProfileData(
    val email: String,
    val customerName: String,
    var phoneNumber: String,
    var password: Int
)

data class AnimalData(
    val animalId: Int,
    val customerId: Int,
    val animalName: String,
    val animalSpecies: String,
    val animalBirthdate: LocalDate,
    val gender: String
)

class ProfileViewModel : ViewModel() {
    val profileDataState = MutableStateFlow<ProfileData>(ProfileData("", "", "", 0))
    val animalDataState = MutableStateFlow<List<AnimalData>>(emptyList())
    val hotelImages = listOf(R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3)

    fun updateAnimalDataState(updateList: List<AnimalData>) {
        animalDataState.value = updateList
    }

    fun getAnimalInfo(): List<AnimalData> {
        return animalDataState.value
    }

    fun getAnimalInfoByID(dogID: Int): AnimalData? {
        return animalDataState.value.find { it.animalId == dogID }
    }


}