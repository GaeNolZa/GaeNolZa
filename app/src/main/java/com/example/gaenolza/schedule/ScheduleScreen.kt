package com.example.gaenolza.schedule

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.Screen
import com.example.gaenolza.network.sendReservationData
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.ui.theme.GaeNolZaTheme
import com.example.gaenolza.viewmodel.ProfileViewModel
import com.example.gaenolza.viewmodel.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen(
    reservationViewModel: ReservationViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {
    val onReservationInfoState = reservationViewModel.onReservationState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //        ScheduleScreenHeader(onBackClick = { navHostController.navigate(Screen.Main.route) })
            //        Spacer(modifier = Modifier.height(8.dp))
            //        HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            CalendarComponent(reservationViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Box(modifier = Modifier
                .width(120.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        navController.navigate(Screen.AnimalList.route)
                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.sample_dog_image),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val context = LocalContext.current
                Text(
                    text = "${onReservationInfoState.reservationLong}박 ${onReservationInfoState.reservationLong + 1}일 / ${onReservationInfoState.reservationLong * onReservationInfoState.hotelPricePerDay}원",
                )
                if (onReservationInfoState.animalId!=0) {
                    Text(text = profileViewModel.getAnimalInfoByID(onReservationInfoState.animalId).animalName)
                }
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        val customScope = CoroutineScope(Dispatchers.Main)
                        // 여기에 원하는 파라미터를 사용하여 sendReservationData를 직접 호출합니다.
                        val onReservationStateSnap = reservationViewModel.onReservationState.value
                        for (i in 0 until onReservationStateSnap.reservationLong) {
                            sendReservationData(
                                facilityId = onReservationStateSnap.hotelId,
                                animalId = onReservationStateSnap.animalId,
                                reservationDate = LocalDate.parse(
                                    onReservationStateSnap.reservationDate1!!.plusDays(
                                        i
                                    ).toString()
                                ),
                                customerId = onReservationStateSnap.customerId
                            ) { result ->
                                result.fold(
                                    onSuccess = {
                                        // 예약 성공 시 처리
                                        println("예약 성공: $it")
                                        customScope.launch{
                                            Toast.makeText(context, "예약 성공", Toast.LENGTH_SHORT).show()
                                            navController.navigate(Screen.Main.route)
                                        }
                                    },
                                    onFailure = { error ->
                                        // 예약 실패 시 처리
                                        println("예약 실패: ${error.message}")
                                    }
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorPalette.primaryPink,
                        disabledContainerColor = Color.Gray
                    ),
                    enabled = if (reservationViewModel.onReservationState.collectAsState().value.reservationDate2 == null) false else true
                ) {
                    Text(text = "예약하기")
                }
            }
        }
    }
}