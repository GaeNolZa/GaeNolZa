package com.example.gaenolza.schedule

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gaenolza.R
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.ui.theme.GaeNolZaTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen() {
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)
        .background(Color.White)) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
    //        ScheduleScreenHeader(onBackClick = { navHostController.navigate(Screen.Main.route) })
    //        Spacer(modifier = Modifier.height(8.dp))
    //        HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            CalendarComponent()
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Box(modifier = Modifier.width(120.dp)){
                Image(painter = painterResource(id = R.drawable.sample_dog_image), contentDescription = "")
            }
            Column(
                modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.Start) {
                Text(text = "3박 4일 / n원",
                    modifier = Modifier.padding(bottom = 20.dp))
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorPalette.primaryPink,
                        disabledContainerColor = Color.Gray
                    )) {
                    Text(text = "예약하기")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScheduleMainScreenPreview() {
    GaeNolZaTheme { ScheduleMainScreen() }
}