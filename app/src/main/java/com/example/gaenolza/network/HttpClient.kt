package com.example.gaenolza.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

object HttpClient {
    private val client = OkHttpClient()

    fun sendPostRequest(url: String, jsonData: String, onResult: (Result<String>) -> Unit) {
        val body = jsonData.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                onResult(Result.failure(e))
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val responseData = it.body?.string()
                        onResult(Result.success(responseData ?: "No response from server"))
                    } else {
                        onResult(Result.failure(IOException("Request failed with code: ${it.code}")))
                    }
                }
            }
        })
    }

    fun sendGetRequest(url: String, onResult: (Result<String>) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                onResult(Result.failure(e))
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val responseData = it.body?.string()
                        onResult(Result.success(responseData ?: "No response from server"))
                    } else {
                        onResult(Result.failure(IOException("Request failed with code: ${it.code}")))
                    }
                }
            }
        })
    }
}