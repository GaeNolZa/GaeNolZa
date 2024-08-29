package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel

data class ProfileData(
    val email: String,
    val customerName: String,
    var phoneNumber: String,
    var password: Int
)

data class AnimalData(
    val animalKey: String,
    val email: String,
    val animalType: String,
    val animalBirth: Int,
    val animalGender: String,
    val animalMemo: String
)

class ProfileViewModel : ViewModel() {

}