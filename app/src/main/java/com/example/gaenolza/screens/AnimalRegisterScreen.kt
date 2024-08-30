package com.example.gaenolza.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimalRegisterScreen(
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("동물정보", style = MaterialTheme.typography.headlineMedium)

        var animalID by remember { mutableStateOf("") }
        OutlinedTextField(
            value = animalID,
            onValueChange = { animalID= it },
            label = { Text("동물ID") },
            modifier = Modifier.fillMaxWidth()
        )

        var userID by remember { mutableStateOf("") }
        OutlinedTextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text("고객ID") },
            modifier = Modifier.fillMaxWidth()
        )

        var animalType by remember { mutableStateOf("") }
        OutlinedTextField(
            value = animalType,
            onValueChange = { animalType = it },
            label = { Text("동물종류") },
            modifier = Modifier.fillMaxWidth()
        )

        var animalBirth by remember { mutableStateOf("") }
        OutlinedTextField(
            value = animalBirth,
            onValueChange = { animalBirth = it },
            label = { Text("동물생일") },
            modifier = Modifier.fillMaxWidth()
        )

        var animalGender by remember { mutableStateOf("") }
        OutlinedTextField(
            value = animalGender,
            onValueChange = { animalGender = it },
            label = { Text("동물성별") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(onClick = { /*TODO*/ }) {
            Text(text = "등록")
        }
    }
}