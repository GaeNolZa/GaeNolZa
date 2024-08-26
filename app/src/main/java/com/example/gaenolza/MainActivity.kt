package com.example.gaenolza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var selectedItem by remember { mutableIntStateOf(0) }
    var showSignUp by remember { mutableStateOf(false) }
    val items = listOf("홈", "호텔", "서비스","프로필")

    val iconResources = listOf(
        R.drawable.ic_home,
        R.drawable.ic_hotel,
        R.drawable.ic_service,
        R.drawable.ic_profile
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = iconResources[index]),
                                contentDescription = item,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            showSignUp = false  // SignUp 화면을 닫습니다.
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showSignUp) {
                SignupScreen(
                    onSignUpComplete = {
                        showSignUp = false
                        selectedItem = 3  // 프로필 탭 (로그인 화면)으로 이동
                    },
                    onBackClick = {
                        showSignUp = false
                        selectedItem = 3  // 프로필 탭 (로그인 화면)으로 이동
                    }
                )
            } else {
                when (selectedItem) {
                    0 -> MainScreen(
                        onCardClick = { cardId ->
                            // 카드 클릭 처리
                            println("Card clicked: $cardId")
                        },
                    )
                    1 -> Text("호텔 화면", modifier = Modifier.align(Alignment.Center))
                    2 -> Text("서비스 화면", modifier = Modifier.align(Alignment.Center))
                    3 -> LoginScreen(
                        onLoginClick = { username, password ->
                            // 로그인 로직 처리
                            println("Login attempt: Username - $username, Password - $password")
                        },
                        onSignUpClick = {
                            showSignUp = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}