package com.example.gaenolza.network

import org.json.JSONObject
import java.time.LocalDate

fun sendCustomerRegistration(
    customerName: String,
    email: String,
    password: String,
    phoneNumber: String,
    onResult: (Result<String>) -> Unit
) {
    val url = "$SERVER_ADDRESS/customer/add"

    val jsonObject = JSONObject().apply {
        put("customerName", customerName)
        put("phoneNumber", phoneNumber)
        put("password", password)
        put("email", email)
    }

    val jsonData = jsonObject.toString()

    HttpClient.sendPostRequest(url, jsonData, onResult)
}

fun sendLoginData(
    email: String,
    password: String,
    onResult: (Result<String>) -> Unit
) {
    val url = "$SERVER_ADDRESS/customer/login"

    val jsonObject = JSONObject().apply {
        put("email", email)
        put("password", password)
    }

    val jsonData = jsonObject.toString()

    HttpClient.sendPostRequest(url, jsonData, onResult)
}

fun sendAnimalData(
    customerId: Int,
    animalName: String,
    animalSpecies: String,
    animalBirthdate: LocalDate,
    gender: String,
    onResult: (Result<String>) -> Unit
) {
    val url = "$SERVER_ADDRESS/animal/add"

    val jsonObject = JSONObject().apply {
        put("customerId", customerId)
        put("animalName", animalName)
        put("animalSpecies", animalSpecies)
        put("animalBirthdate", animalBirthdate)
        put("gender", gender)
    }

    val jsonData = jsonObject.toString()

    HttpClient.sendPostRequest(url, jsonData, onResult)
}

fun sendReservationData(
    facilityId: Int,
    animalId: Int,
    reservationDate: LocalDate,
    customerId: Int,
    onResult: (Result<String>) -> Unit
) {
    val url = "$SERVER_ADDRESS/reservation/add"

    val jsonObject = JSONObject().apply {
        put("facilityId", facilityId)
        put("animalId", animalId)
        put("reservationDate", reservationDate)
        put("customerId", customerId)
    }

    val jsonData = jsonObject.toString()

    HttpClient.sendPostRequest(url, jsonData, onResult)
}