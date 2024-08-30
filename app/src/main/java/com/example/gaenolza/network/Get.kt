package com.example.gaenolza.network

import Facility
import com.example.gaenolza.viewmodel.AnimalData
import org.json.JSONArray
import java.time.LocalDate

fun sendGetAnimalsByCustomerId(
    customerId: Int,
    onResult: (Result<List<AnimalData>>) -> Unit
) {
    val url = "http://192.168.45.240:8080/animal/list/id/$customerId"

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
    val url = "http://192.168.45.240:8080/facility/list"  // 적절한 엔드포인트로 URL 설정

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