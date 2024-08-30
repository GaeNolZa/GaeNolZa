package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class ProfileData(
    val email: String,
    val customerName: String,
    var phoneNumber: String,
    var password: Int
)

data class AnimalData(
    val animalName: String,
    val email: String,
    val animalType: String,
    val animalBirth: Int,
    val animalGender: String,
)

class ProfileViewModel : ViewModel() {
    val profileDataState = MutableStateFlow<ProfileData>(ProfileData("","","",0))
    val animalDataState = MutableStateFlow<AnimalData>(AnimalData("","","",0,""))
}