package com.example.gaenolza

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gaenolza.ui.theme.GaeNolZaTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GaeNolZaTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("홈", "호텔", "서비스", "프로필")

    val iconResources = listOf(
        R.drawable.ic_home,
        R.drawable.ic_hotel,
        R.drawable.ic_service,
        R.drawable.ic_profile
    )

    Scaffold(
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
                    barColor = MaterialTheme.colorScheme.primary,
                    ballColor = MaterialTheme.colorScheme.primary
                ) {
                    items.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    selectedItem = index
                                    when (index) {
                                        0 -> navController.navigate("main")
                                        1 -> navController.navigate("hotel")
                                        2 -> navController.navigate("service")
                                        3 -> navController.navigate("profile")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = iconResources[index]),
                                    contentDescription = item,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selectedItem == index)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item,
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
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainScreen(
                    navController = navController,
                    onCardClick = { cardId ->
                        println("Card clicked: $cardId")
                    }
                )
            }
            composable("hotel") {
                Text("호텔 화면", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
            }
            composable("service") {
                Text("서비스 화면", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
            }
            composable("profile") {
                LoginScreen(
                    onLoginClick = { name, email, password ->
                        println("Login attempt: Name - $name, Email - $email, Password - $password")
                    },
                    onGoogleSignInClick = {
                        println("Google Sign-In clicked")
                    },
                    onSignUpClick = {
                        navController.navigate("signup")
                    },
                    onFingerprintClick = {
                        println("Fingerprint authentication clicked")
                    }
                )
            }
            composable("signup") {
                SignupScreen(
                    onSignUpComplete = {
                        navController.navigate("profile")
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable("chatScreen") {
                ChatScreen()
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GaeNolZaTheme {
        MyApp()
    }
}