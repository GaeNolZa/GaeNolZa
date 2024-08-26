package com.example.gaenolza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var showSignUp by remember { mutableStateOf(false) }
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
                                    showSignUp = false // Close SignUp screen if open
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
                            println("Card clicked: $cardId")
                        },
                    )
                    1 -> Text("호텔 화면", modifier = Modifier.align(Alignment.Center))
                    2 -> Text("서비스 화면", modifier = Modifier.align(Alignment.Center))
                    3 -> LoginScreen(
                        onLoginClick = { username, password ->
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