package com.example.gaenolza.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.network.sendGetAnimalsByCustomerId
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.viewmodel.ProfileViewModel

@Composable
fun MyPageScreen(
    navController: NavController,
    onLogoutClick: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(60.dp))
        MyPageMain(
            onLogoutClick = onLogoutClick,
            navController,
            profileViewModel
        )
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(ColorPalette.primaryPink),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "마이페이지",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DogsList(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    // Fetch animals data
    LaunchedEffect(Unit) {
        sendGetAnimalsByCustomerId(7) { result ->
            result.fold(
                onSuccess = { fetchedAnimals ->
                    profileViewModel.updateAnimalDataState(fetchedAnimals)
                },
                onFailure = { error ->
                    println("Error fetching animals: ${error.message}")
                }
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Render DogProfile for each animal
        profileViewModel.animalDataState.collectAsState().value.forEach { animal ->
            DogProfile(navController, name = animal.animalName, dogID = animal.animalId)
        }

        // Add an additional DogProfile for adding a new pet
        DogProfile(navController)
    }
}

@Composable
fun DogProfile(
    navController: NavController,
    name: String = "애완동물 추가",
    dogID: Int = 0,
    imageRes: Int = R.drawable.ic_add
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .drawBehind {
                    val dashWidth = 25f
                    val dashGap = 20f
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
                    drawRoundRect(
                        color = Color.Gray,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            pathEffect = pathEffect
                        ),
                        cornerRadius = CornerRadius(5f, 5f)
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (name == "애완동물 추가") {
                            navController.navigate("animalRegister")
                        } else navController.navigate("dog/$dogID")
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = imageRes),
                tint = Color.Gray,
                contentDescription = name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MainButtons(onLogoutClick: () -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //버튼 함수화
        MyPageButton(text = "프로필", onClickSuccess = { })
        MyPageButton(text = "예약내역", onClickSuccess = { navController.navigate("reservation") })
        MyPageButton(text = "로그아웃", onClickSuccess = { onLogoutClick() }, color = Color.LightGray)
    }
}

@Composable
fun MyPageMain(
    onLogoutClick: () -> Unit,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    DogsList(
        navController,
        profileViewModel
    )
    Spacer(modifier = Modifier.height(20.dp))
    MainButtons(onLogoutClick = onLogoutClick, navController = navController)
}

@Composable
fun MyPageButton(
    text: String,
    onClickSuccess: () -> Unit,
    color: Color = ColorPalette.primaryPink
) {
    Button(
        onClick = { onClickSuccess() },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = text, color = Color.Black, fontSize = 18.sp)
    }
}
