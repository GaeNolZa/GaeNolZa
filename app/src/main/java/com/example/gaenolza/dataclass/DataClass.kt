package com.example.gaenolza.dataclass

import java.time.LocalDate

data class Reservation(
    var reservationId: Int,
    var facilityId: Int,
    var animalId: Int,
    var reservationDate: LocalDate,
    var customerId: Int,
)