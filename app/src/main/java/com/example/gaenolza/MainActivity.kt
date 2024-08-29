package com.example.gaenolza

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.compose.rememberNavController
import com.example.gaenolza.screens.*
import com.example.gaenolza.screens.chatbot.ChatBotScreen
import com.example.gaenolza.ui.theme.GaeNolZaTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius

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
    data object Main : Screen("홈 화면", R.drawable.ic_home_renew)
    data object Hotel : Screen("호텔", R.drawable.ic_hotel)
    data object Service : Screen("서비스", R.drawable.ic_service)
    data object Profile : Screen("마이 페이지", R.drawable.ic_profile)
    data object SignUp : Screen("회원 가입", null)
    data object ChatBot : Screen("챗봇", null)
}

@Composable
fun GaeNolZaMain() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(Screen.Main, Screen.Hotel, Screen.Service, Screen.Profile)

    Scaffold(
        modifier = Modifier.padding(bottom = 30.dp),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                LoginScreen(
                    onLoginClick = { name, email, password ->
                        println("Login attempt: Name - $name, Email - $email, Password - $password")
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
            composable(Screen.SignUp.route) { SignupScreen(navController) }
            composable(Screen.ChatBot.route) { ChatBotScreen() }
            composable(Screen.Service.route) { ServiceScreen() }
            composable(Screen.Hotel.route) { HotelScreen() }
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