package com.example.gaenolza

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(onSignUpComplete: () -> Unit, onBackClick: () -> Unit) {
    var customerId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var petGender by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var petBirthday by remember { mutableStateOf("") }
    var isVaccinated by remember { mutableStateOf("") }
    var specialNotes by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBackClick) {
                Text(text = "돌아가기")
            }
            Text("회원가입", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.width(48.dp))
        }

        OutlinedTextField(
            value = customerId,
            onValueChange = { customerId = it },
            label = { Text("고객 ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("반려동물 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("반려동물 성별", style = MaterialTheme.typography.bodyLarge)
        Row {
            RadioButton(
                selected = petGender == "수컷",
                onClick = { petGender = "수컷" }
            )
            Text("수컷", modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = petGender == "암컷",
                onClick = { petGender = "암컷" }
            )
            Text("암컷", modifier = Modifier.align(Alignment.CenterVertically))
        }

        OutlinedTextField(
            value = petType,
            onValueChange = { petType = it },
            label = { Text("반려동물 종류") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = petBirthday,
            onValueChange = { petBirthday = it },
            label = { Text("반려동물 생일 (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("예방접종 여부", style = MaterialTheme.typography.bodyLarge)
        Row {
            RadioButton(
                selected = isVaccinated == "예",
                onClick = { isVaccinated = "예" }
            )
            Text("예", modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = isVaccinated == "아니오",
                onClick = { isVaccinated = "아니오" }
            )
            Text("아니오", modifier = Modifier.align(Alignment.CenterVertically))
        }

        OutlinedTextField(
            value = specialNotes,
            onValueChange = { specialNotes = it },
            label = { Text("특이사항") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("돌아가기")
            }
            Button(
                onClick = onSignUpComplete,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text("가입하기")
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}