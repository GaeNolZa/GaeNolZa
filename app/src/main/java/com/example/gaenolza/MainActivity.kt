package com.example.gaenolza

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gaenolza.network.sendLoginData
import com.example.gaenolza.screens.HotelScreen
import com.example.gaenolza.screens.LoginScreen
import com.example.gaenolza.screens.MyPageScreen
import com.example.gaenolza.screens.ServiceScreen
import com.example.gaenolza.screens.SignupScreen
import com.example.gaenolza.screens.chatbot.ChatBotScreen
import com.example.gaenolza.ui.theme.GaeNolZaTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
        enableEdgeToEdge()
        setContent {
            GaeNolZaTheme {
                GaeNolZaMain()
            }
        }
    }
}

sealed class Screen(val route: String, val iconResourceId: Int?) {
    data object Main : Screen("main", R.drawable.ic_home_renew)
    data object Hotel : Screen("hotel", R.drawable.ic_hotel)
    data object Service : Screen("service", R.drawable.ic_service)
    data object Profile : Screen("profile", R.drawable.ic_profile)
    data object SignUp : Screen("signup", null)
    data object ChatBot : Screen("chatScreen", null)
    data object MyPage : Screen("myPage", null)
}

@Composable
fun GaeNolZaMain() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(Screen.Main, Screen.Hotel, Screen.Service, Screen.Profile)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.padding(bottom = 30.dp),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
            ) {
                AnimatedNavigationBar(
                    modifier = Modifier.height(70.dp),
                    selectedIndex = selectedItem,
                    cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                    ballAnimation = Parabolic(tween(300)),
                    indentAnimation = Height(tween(300)),
                    barColor = Color(0xFF393939),
                    ballColor = Color(0xFFFF5BA0)
                ) {
                    items.forEachIndexed { index, screen ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        selectedItem = index
                                        navController.navigate(screen.route)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = screen.iconResourceId!!),
                                    contentDescription = screen.route,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selectedItem == index)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = screen.route,
                                    color = if (selectedItem == index)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            if (currentDestination != "chatScreen") {
                FloatingActionButton(
                    onClick = { navController.navigate("chatScreen") },
                    shape = CircleShape,
                    modifier = Modifier.Companion
    //                    .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(72.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Add",
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Main.route) {
                HomeScreen(
                    navController = navController,
                    onCardClick = { cardId ->
                        println("Card clicked: $cardId")
                    }
                )
            }
            composable(Screen.Profile.route) {
                if (isLoggedIn) { // 로그인 상태에 따른 분기 처리
                    navController.navigate(Screen.MyPage.route) // 로그인 상태일 경우 MyPage로 이동
                } else {
                    LoginScreen(
                        onLoginClick = { _, email, password ->
                            CoroutineScope(Dispatchers.IO).launch {
                                sendLoginData(
                                    email = email,
                                    password = password
                                ) { result ->
                                    result.fold(
                                        onSuccess = { responseData ->
                                            println("Login successful: $responseData")
                                            isLoggedIn = true // 로그인 성공 시 상태 업데이트
                                            CoroutineScope(Dispatchers.Main).launch {
                                                navController.navigate(Screen.MyPage.route) {
                                                    popUpTo(Screen.Profile.route) {
                                                        inclusive = true
                                                    }
                                                    // 이전 Profile 화면을 스택에서 제거하여 뒤로가기 시 돌아가지 않도록 설정
                                                }
                                            }
                                        },
                                        onFailure = { error ->
                                            println("Login failed: ${error.message}")
                                        }
                                    )
                                }
                            }
                        },
                        onGoogleSignInClick = {
                            println("Google Sign-In clicked")
                        },
                        onSignUpClick = {
                            navController.navigate(Screen.SignUp.route)
                        },
                        onFingerprintClick = {
                            println("Fingerprint authentication clicked")
                        }
                    )
                }
            }
            composable(Screen.SignUp.route) { SignupScreen(navController) }
            composable(Screen.ChatBot.route) { ChatBotScreen() }
            composable(Screen.Service.route) { ServiceScreen() }
            composable(Screen.Hotel.route) { HotelScreen() }
            composable(Screen.MyPage.route) {
                MyPageScreen(
                    onLogoutClick = {
                        isLoggedIn = false
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.MyPage.route) { inclusive = true }
                            // MyPage 화면도 스택에서 제거하고 Main으로 이동
                        }
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GaeNolZaTheme {
        GaeNolZaMain()
    }
}