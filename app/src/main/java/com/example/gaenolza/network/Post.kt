package com.example.gaenolza.network

import org.json.JSONObject

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