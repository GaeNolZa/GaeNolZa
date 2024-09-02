package com.example.gaenolza.network

import com.example.gaenolza.dataclass.Reservation
import com.example.gaenolza.viewmodel.AnimalData
import com.example.gaenolza.viewmodel.Facility
import com.example.gaenolza.viewmodel.ReservationData
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

fun sendGetAnimalsByCustomerId(
    customerId: Int,
    onResult: (Result<List<AnimalData>>) -> Unit
) {
    val url = "$SERVER_ADDRESS/animal/list/id/$customerId"

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonArray = JSONArray(responseData)
                    val animals = mutableListOf<AnimalData>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val animal = AnimalData(
                            animalId = jsonObject.getInt("animalId"),
                            customerId = jsonObject.getInt("customerId"),
                            animalName = jsonObject.getString("animalName"),
                            animalSpecies = jsonObject.getString("animalSpecies"),
                            animalBirthdate = LocalDate.parse(jsonObject.getString("animalBirthdate")),
                            gender = jsonObject.getString("gender")
                        )
                        animals.add(animal)
                    }

                    onResult(Result.success(animals))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            },
            onFailure = { error ->
                onResult(Result.failure(error))
            }
        )
    }
}

fun sendGetFacilities(
    onResult: (Result<List<Facility>>) -> Unit
) {
    val url = "$SERVER_ADDRESS/facility/list"  // 적절한 엔드포인트로 URL 설정

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonArray = JSONArray(responseData)
                    val facilities = mutableListOf<Facility>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val facility = Facility(
                            facilityId = jsonObject.getInt("facilityId"),
                            address = jsonObject.getString("address"),
                            facilityName = jsonObject.getString("facilityName"),
                            facilityContact = jsonObject.getString("facilityContact"),
                            ownedFacility = jsonObject.getString("ownedFacility"),
                            rating = jsonObject.getDouble("rating").toFloat(),
                            reviewCount = jsonObject.getInt("reviewCount"),
                            numberOfRooms = jsonObject.getInt("numberOfRooms")
                        )
                        facilities.add(facility)
                    }

                    onResult(Result.success(facilities))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            },
            onFailure = { error ->
                onResult(Result.failure(error))
            }
        )
    }
}

fun sendGetFacilities(
    name: String, // 검색어를 추가로 받도록 함
    onResult: (Result<List<Facility>>) -> Unit
) {
    val url = "$SERVER_ADDRESS/facility/search?name=$name"  // 검색어에 따라 적절한 엔드포인트로 URL 설정

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonArray = JSONArray(responseData)
                    val facilities = mutableListOf<Facility>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val facility = Facility(
                            facilityId = jsonObject.getInt("facilityId"),
                            address = jsonObject.getString("address"),
                            facilityName = jsonObject.getString("facilityName"),
                            facilityContact = jsonObject.getString("facilityContact"),
                            ownedFacility = jsonObject.getString("ownedFacility"),
                            rating = jsonObject.getDouble("rating").toFloat(),
                            reviewCount = jsonObject.getInt("reviewCount"),
                            numberOfRooms = jsonObject.getInt("numberOfRooms")
                        )
                        facilities.add(facility)
                    }

                    onResult(Result.success(facilities))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            },
            onFailure = { error ->
                onResult(Result.failure(error))
            }
        )
    }
}

fun sendGetReservations(
    customerId: Int, // 고객 ID를 인자로 받음
    onResult: (Result<List<ReservationData>>) -> Unit
) {
    val url = "$SERVER_ADDRESS/reservation/list/customer/$customerId" // 고객 ID에 따라 적절한 엔드포인트로 URL 설정

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonArray = JSONArray(responseData)
                    val reservations = mutableListOf<ReservationData>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val reservation = ReservationData(
                            reservationId = jsonObject.getInt("reservationId"),
                            facilityId = jsonObject.getInt("facilityId"),
                            animalId = jsonObject.getInt("animalId"),
                            reservationDate = LocalDate.parse(jsonObject.getString("reservationDate")),
                            customerId = jsonObject.getInt("customerId")
                        )
                        reservations.add(reservation)
                    }

                    onResult(Result.success(reservations))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            },
            onFailure = { error ->
                onResult(Result.failure(error))
            }
        )
    }
}

fun sendGetFacilityById(
    facilityId: Int, // 시설 ID를 인자로 받음
    onResult: (Result<Facility?>) -> Unit
) {
    val url = "$SERVER_ADDRESS/facility/find/id/$facilityId" // 시설 ID에 따라 적절한 엔드포인트로 URL 설정

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonObject = JSONObject(responseData)
                    val facility = Facility(
                        facilityId = jsonObject.getInt("facilityId"),
                        address = jsonObject.getString("address"),
                        facilityName = jsonObject.getString("facilityName"),
                        facilityContact = jsonObject.getString("facilityContact"),
                        ownedFacility = jsonObject.getString("ownedFacility"),
                        rating = jsonObject.getDouble("rating").toFloat(),
                        reviewCount = jsonObject.getInt("reviewCount"),
                        numberOfRooms = jsonObject.getInt("numberOfRooms")
                    )
                    onResult(Result.success(facility))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            },
            onFailure = { error ->
                onResult(Result.failure(error))
            }
        )
    }
}
