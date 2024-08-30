package com.example.gaenolza.network

import com.example.gaenolza.screens.Animal
import org.json.JSONArray
import java.time.LocalDate

fun sendGetAnimalsByCustomerId(
    customerId: Int,
    onResult: (Result<List<Animal>>) -> Unit
) {
    val url = "http://192.168.45.240:8080/animal/list/id/$customerId"

    HttpClient.sendGetRequest(url) { result ->
        result.fold(
            onSuccess = { responseData ->
                try {
                    val jsonArray = JSONArray(responseData)
                    val animals = mutableListOf<Animal>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val animal = Animal(
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