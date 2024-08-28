package com.example.gaenolza.screens.chatbot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChatBotScreen() {
    val coroutineScope = rememberCoroutineScope()
    val chatHistory = remember { mutableStateListOf<String>() }
    var userInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(chatHistory) { message ->
                Text(text = message, modifier = Modifier.padding(8.dp))
            }
        }

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("메시지를 입력하세요") },
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (userInput.isNotEmpty() && !isLoading) {
                            sendMessage(coroutineScope, userInput, chatHistory) {
                                isLoading = it
                                if (!it) userInput = ""
                            }
                        }
                    }
                )
            )
            Button(
                onClick = {
                    if (userInput.isNotEmpty() && !isLoading) {
                        sendMessage(coroutineScope, userInput, chatHistory) {
                            isLoading = it
                            if (!it) userInput = ""
                        }
                    }
                },
                enabled = userInput.isNotEmpty() && !isLoading
            ) {
                Text("Send")
            }
        }
    }
}

private fun sendMessage(
    coroutineScope: CoroutineScope,
    userInput: String,
    chatHistory: SnapshotStateList<String>,
    updateLoadingState: (Boolean) -> Unit
) {
    coroutineScope.launch {
        updateLoadingState(true)
        chatHistory.add("You: $userInput")

        try {
            val translatedInput = translateAtoB(userInput, "Korean", "English")
            val serverResponse = sendMessageToServer(translatedInput)
            val translatedResponse = translateAtoB(serverResponse, "English", "Korean")
            chatHistory.add("Bot: $translatedResponse")
        } catch (e: Exception) {
            chatHistory.add("Error: ${e.localizedMessage}")
        } finally {
            updateLoadingState(false)
        }
    }
}