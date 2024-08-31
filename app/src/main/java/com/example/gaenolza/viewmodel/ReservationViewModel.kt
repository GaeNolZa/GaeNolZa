package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate


data class ReservationData(
    var reservationId: Int,
    var facilityId: Int,
    var animalId: Int,
    var reservationDate: LocalDate,
    var customerId: Int
)

data class OnReservationInfo(
    var reservationStartDate: LocalDate,
    var reservationLong : Long,
    var reservationPrice : Int
)

class ReservationViewModel : ViewModel(){
    val reservationDateState = MutableStateFlow<List<ReservationData>>(emptyList())
    val onReservationState = MutableStateFlow<OnReservationInfo>(OnReservationInfo(LocalDate.now(),0,0))
}