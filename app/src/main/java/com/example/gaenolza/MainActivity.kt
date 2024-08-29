package com.example.gaenolza

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gaenolza.screens.HomeScreen
import com.example.gaenolza.screens.HotelScreen
import com.example.gaenolza.screens.LoginScreen
import com.example.gaenolza.screens.ServiceScreen
import com.example.gaenolza.screens.SignupScreen
import com.example.gaenolza.screens.chatbot.ChatBotScreen
import com.example.gaenolza.ui.theme.GaeNolZaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    data object Chat : Screen("chatScreen", null)
}

@Composable
fun GaeNolZaMain() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(Screen.Main, Screen.Hotel, Screen.Service, Screen.Profile)

    Scaffold(
        modifier = Modifier.padding(bottom = 30.dp),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
            ) {
                var isTriggered by remember { mutableStateOf(false) }
                var ballCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
                CurvedBox(isTriggered, ballCoordinates)
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()) {
                    ContentBall(onTap = {isTriggered = !isTriggered},
                        sendCoordinate = { it -> ballCoordinates = it})
                    ContentBall(onTap = {isTriggered = !isTriggered},
                        sendCoordinate = { it -> ballCoordinates = it})
                }
//                NavigationBarTest()
//                AnimatedNavigationBar(
//                    modifier = Modifier.height(70.dp),
//                    selectedIndex = selectedItem,
//                    cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
//                    ballAnimation = Parabolic(tween(300)),
//                    indentAnimation = Height(tween(300)),
//                    barColor = MaterialTheme.colorScheme.primary,
//                    ballColor = MaterialTheme.colorScheme.primary
//                ) {
//                    items.forEachIndexed { index, screen ->
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .pointerInput(Unit) {
//                                    detectTapGestures {
//                                        selectedItem = index
//                                        navController.navigate(screen.route)
//                                    }
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = screen.iconResourceId!!),
//                                    contentDescription = screen.route,
//                                    modifier = Modifier.size(24.dp),
//                                    tint = if (selectedItem == index)
//                                        MaterialTheme.colorScheme.onPrimary
//                                    else
//                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
//                                )
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    text = screen.route,
//                                    color = if (selectedItem == index)
//                                        MaterialTheme.colorScheme.onPrimary
//                                    else
//                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
//                                    fontSize = 12.sp,
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        }
//                    }
//                }
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
            composable(Screen.Hotel.route) { HotelScreen() }
            composable(Screen.Service.route) { ServiceScreen() }
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
            composable(Screen.SignUp.route) {
                SignupScreen(
                    onSignUpComplete = {
                        navController.navigate(Screen.Profile.route)
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Chat.route) { ChatBotScreen() }
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

@Composable
fun NavigationBarTest() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(color = Color.Red),
        contentAlignment = Alignment.BottomStart
    ) {

//        ContentBall()
    }
}

@Composable
fun ContentBall(onTap:() -> Unit,
                sendCoordinate:(LayoutCoordinates) -> Unit) {
    var isTriggered by remember { mutableStateOf(false) }
    val offsetY by animateDpAsState(
        targetValue = if (isTriggered) -60.dp else 0.dp,
        label = ""
    )

    Box(modifier = Modifier
        .offset(y = offsetY)
        .size(60.dp)
        .clip(shape = CircleShape)
        .background(
            color = if (isTriggered) Color.Blue else Color.Yellow
        )
        .pointerInput(Unit) {
            detectTapGestures {
                isTriggered = !isTriggered
                onTap()
                Log.d("trigger test", isTriggered.toString())
                Log.d("offset test", offsetY.toString())
            }
        }
        .onGloballyPositioned { layoutCoordinates ->
            sendCoordinate(layoutCoordinates)
        }
    )
}

@Composable
fun CurvedBox(isTriggered: Boolean,
              ballCoordinates: LayoutCoordinates?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(Color.Transparent)
    ) {
        val offsetY by animateFloatAsState(targetValue = if (isTriggered) 70f else 0f,
            animationSpec = tween(delayMillis = 500),
            label = ""
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingValue = 16.dp
            val ballX = ballCoordinates?.positionInWindow()?.x ?: 0f
            val ballWidth = ballCoordinates?.size?.width?.toFloat() ?: 0f
            val ballCenterX = ballX + (ballWidth/2) - paddingValue.toPx()
            val leftX = ballCenterX - (ballWidth/2) - 20f
            val rightX = ballCenterX + (ballWidth/2) + 20f

            val clipPath = Path().apply {
                addRoundRect(
                    roundRect = RoundRect(rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(34.dp.toPx(), 34.dp.toPx())
                )
                )
            }

            // clipPath를 사용하여 둥근 사각형 내부에서 그리기
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color.Transparent,
                    size = size,
                    cornerRadius = CornerRadius(34.dp.toPx(), 34.dp.toPx())
                )

                // 움푹 들어간 곡선 그리기
                val path = Path().apply {
                    lineTo(leftX, 0f)
                    cubicTo(
                        leftX + 10f , 0f,
                        ballCenterX - (ballWidth/2) - 10f , offsetY,
                        ballCenterX , offsetY
                    )
                    cubicTo(
                        ballCenterX + (ballWidth/2) + 10f, offsetY,
                        rightX - 10f, 0f,
                        rightX, 0f
                    )
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                // 움푹 들어간 경로를 투명하게 그려서 영역 제거
                drawPath(path = path, color = Color.Red)
            }
        }
    }
}