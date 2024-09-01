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
    var reservationDate1: LocalDate?,
    var reservationDate2: LocalDate?,
    var reservationLong: Long,
    var reservationPrice: Int,
    var animalId: Int,
    var hotelId: Int,
    var customerId: Int,
    var hotelPricePerDay: Int
)

class ReservationViewModel : ViewModel(){
    val reservationDateState = MutableStateFlow<List<ReservationData>>(emptyList())
    val onReservationState = MutableStateFlow<OnReservationInfo>(
        OnReservationInfo(null,null,0,0,0,0,7,0))

    fun updateOnReservationDataState(updateData: OnReservationInfo) {
        onReservationState.value = updateData
    }

    fun updatePathHotelId(updateData: Int) {
        onReservationState.value = onReservationState.value.copy(hotelId = updateData)
    }

    fun updatePathAnimalId(updateData: Int) {
        onReservationState.value = onReservationState.value.copy(animalId = updateData)
    }

    fun updatePathCustomerId(updateData: Int) {
        onReservationState.value = onReservationState.value.copy(customerId = updateData)
    }

    fun updatePathHotelPrice(updateData: Int) {
        onReservationState.value = onReservationState.value.copy(hotelPricePerDay = updateData)
    }

    fun updateReservationDataList(updateList: List<ReservationData>) {
        reservationDateState.value = updateList
    }

    fun getReservationInfoByFacilityId(facilityId: Int): ReservationData? {
        return reservationDateState.value.find { it.facilityId == facilityId }
    }
}
