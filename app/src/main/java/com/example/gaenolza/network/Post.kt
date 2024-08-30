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
    val url = "http://192.168.45.240:8080/customer/add"

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
    val url = "http://192.168.45.240:8080/customer/login"

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
    val url = "http://192.168.45.240:8080/animal/add"

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