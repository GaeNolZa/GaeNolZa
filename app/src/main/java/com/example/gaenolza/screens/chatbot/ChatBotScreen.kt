import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gaenolza.screens.chatbot.sendMessageToServer
import com.example.gaenolza.screens.chatbot.translateAtoB
import com.example.gaenolza.ui.theme.ColorPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ChatMessage(
    val content: String,
    val isFromUser: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen() {
    val coroutineScope = rememberCoroutineScope()
    val chatHistory = remember { mutableStateListOf<ChatMessage>() }
    var userInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        // Chat history
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            reverseLayout = true
        ) {
            items(chatHistory.asReversed()) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Input area
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("무엇을 도와드릴까요?") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                IconButton(
                    onClick = {
                        if (userInput.isNotEmpty() && !isLoading) {
                            sendMessage(coroutineScope, userInput, chatHistory) {
                                isLoading = it
                                if (!it) userInput = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(ColorPalette.primaryPink)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isFromUser) ColorPalette.primaryPink else Color.White
    val textColor = if (message.isFromUser) Color.White else Color.Black
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Surface(
            color = bubbleColor,
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = message.content,
                color = textColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

private fun sendMessage(
    coroutineScope: CoroutineScope,
    userInput: String,
    chatHistory: SnapshotStateList<ChatMessage>,
    updateLoadingState: (Boolean) -> Unit
) {
    coroutineScope.launch {
        updateLoadingState(true)
        chatHistory.add(ChatMessage(userInput, isFromUser = true))

        try {
            val translatedInput = translateAtoB(userInput, "Korean", "English")
            val serverResponse = sendMessageToServer(translatedInput)
            val translatedResponse = translateAtoB(serverResponse, "English", "Korean")
            chatHistory.add(ChatMessage(translatedResponse, isFromUser = false))
        } catch (e: Exception) {
            chatHistory.add(ChatMessage("Error: ${e.localizedMessage}", isFromUser = false))
        } finally {
            updateLoadingState(false)
        }
    }
}