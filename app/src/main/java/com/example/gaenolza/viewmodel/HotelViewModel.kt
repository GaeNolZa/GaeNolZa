package com.example.gaenolza.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gaenolza.R
import kotlinx.coroutines.flow.MutableStateFlow

data class HotelData(
    val id: Int,
    val name: String,
    val rating: Float,
    val reviewCount: Int,
    val price: Int,
    var imageResId: Int
)


class HotelViewModel : ViewModel() {
    val hotelDataListState = MutableStateFlow<List<HotelData>>(emptyList())

    fun getHotelInfo(): List<HotelData> {
        return hotelDataListState.value
    }

    fun updateHotelDataState(updateList: List<HotelData>) {
        //hotel image 강제 반영용
        val hotelImages = listOf(R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3)
        for (i in 0..2) {
            updateList[i].imageResId = hotelImages[i]
        }
        hotelDataListState.value = updateList
    }

    fun getHotelInfoByID(HotelId: Int): HotelData {
        return hotelDataListState.value.find { it.id == HotelId }
            ?: HotelData(0, "error", 0f, 0, 0, 0)
    }
}