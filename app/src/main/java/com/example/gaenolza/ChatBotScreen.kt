package com.example.gaenolza

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

@Composable
fun ChatScreen() {
    var userInput by remember { mutableStateOf("") }
    val chatHistory = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(chatHistory) { message ->
                Text(text = message, modifier = Modifier.padding(8.dp))
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter your message") }
            )
            Button(
                onClick = {
                    if (userInput.isNotEmpty()) {
                        chatHistory.add("You: $userInput")
                        sendMessageToServer(userInput, chatHistory)
                        userInput = ""
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

fun sendMessageToServer(message: String, chatHistory: SnapshotStateList<String>) {
    val chatMessage = ChatMessage(message)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.sendMessage(chatMessage)
            withContext(Dispatchers.Main) {
                chatHistory.add("Bot: ${response.response}")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                chatHistory.add("Error: Could not connect to the server")
            }
        }
    }
}

// Retrofit 설정
object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.45.37:5000/") // 여기에 서버의 IP 주소와 포트를 입력
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ChatbotApi = retrofit.create(ChatbotApi::class.java)
}

interface ChatbotApi {
    @POST("/chat")
    suspend fun sendMessage(@Body message: ChatMessage): ChatResponse
}

data class ChatMessage(val message: String)
data class ChatResponse(val response: String)
