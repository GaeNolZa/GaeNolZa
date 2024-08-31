package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gaenolza.R
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
    var reservationDate1: LocalDate?,
    var reservationDate2: LocalDate?,
    var reservationLong: Long,
    var reservationPrice: Int,
    var animalId: Int,
    var hotelId: Int,
    var customerId: Int
)

class ReservationViewModel : ViewModel(){
    val reservationDateState = MutableStateFlow<List<ReservationData>>(emptyList())
    val onReservationState = MutableStateFlow<OnReservationInfo>(
        OnReservationInfo(null,null,0,0,0,0,0))

    fun updateOnReservationDataState(updateData: OnReservationInfo) {
        onReservationState.value = updateData
    }

    fun updateHotelPath(updateData: Int) {
        onReservationState.value = onReservationState.value.copy(hotelId = updateData)
    }
}
