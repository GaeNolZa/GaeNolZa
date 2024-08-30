package com.example.gaenolza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gaenolza.R
import com.example.gaenolza.ui.theme.ColorPalette

@Composable
fun MyPageScreen(
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(20.dp))
        MyPageMain(onLogoutClick = onLogoutClick)
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
fun DogsList() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DogProfile("루이", R.drawable.ic_launcher_background)
        DogProfile("찰리", R.drawable.ic_launcher_background)
        DogProfile("밀크", R.drawable.ic_launcher_background)
    }
}

@Composable
fun DogProfile(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MainButtons(onLogoutClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //버튼 함수화
        MyPageButton(text = "프로필", onClickSuccess = { })
        MyPageButton(text = "예약내역", onClickSuccess = {  })
        MyPageButton(text = "로그아웃", onClickSuccess = { onLogoutClick() }, color = Color.LightGray)
    }
}

@Composable
fun MyPageMain(onLogoutClick: () -> Unit) {
    DogsList()
    Spacer(modifier = Modifier.height(20.dp))
    MainButtons(onLogoutClick = onLogoutClick)
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
