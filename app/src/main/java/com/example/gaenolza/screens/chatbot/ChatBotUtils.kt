package com.example.gaenolza.screens.chatbot

import com.google.gson.JsonObject
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun translateAtoB(inputText: String, inputLanguage: String, outputLanguage: String): String {
    return withContext(Dispatchers.Default) {
        val languageMap = mapOf(
            "English" to TranslateLanguage.ENGLISH,
            "Korean" to TranslateLanguage.KOREAN,
            // Add more languages here as needed
        )

        val sourceLanguageCode = languageMap[inputLanguage] ?: throw IllegalArgumentException("Invalid input language")
        val targetLanguageCode = languageMap[outputLanguage] ?: throw IllegalArgumentException("Invalid output language")

        val translator = Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(targetLanguageCode)
                .build()
        )

        try {
            downloadModelIfNeeded(translator)
            translate(translator, inputText)
        } finally {
            translator.close()
        }
    }
}

suspend fun translate(translator: Translator, text: String): String = suspendCoroutine { continuation ->
    translator.translate(text)
        .addOnSuccessListener { translatedText ->
            continuation.resume(translatedText)
        }
        .addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
}

suspend fun downloadModelIfNeeded(translator: Translator) = suspendCoroutine { continuation ->
    val conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()
    translator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
            continuation.resume(Unit)
        }
        .addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
}

suspend fun sendMessageToServer(message: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val jsonBody = JsonObject().apply {
                addProperty("question", message)
            }
            val response = RetrofitInstance.api.sendMessage(jsonBody)
            response.get("answer").asString
        } catch (e: Exception) {
            "Error: Could not connect to the server"
        }
    }
}

// Retrofit 설정
val BASE_URL = "http://192.168.45.219:5000/" // 서버 IP 주소 업데이트

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ChatbotApi = retrofit.create(ChatbotApi::class.java)
}

interface ChatbotApi {
    @POST("/get_answer")
    suspend fun sendMessage(@Body message: JsonObject): JsonObject
}
