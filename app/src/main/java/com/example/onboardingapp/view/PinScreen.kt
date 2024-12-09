package com.example.onboardingapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onboardingapp.viewmodel.UserViewModel

@Composable
fun PinScreen(
    userViewModel: UserViewModel,
    onPinCorrect: () -> Unit,
) {
    val mostRecentUser by userViewModel.getMostRecentUser().observeAsState()

    var pin by remember { mutableStateOf("") }
    var isPinValid by remember { mutableStateOf(true) }
    var isPinIncorrect by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = pin,
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    pin = it
                    isPinValid = it.length == 4
                    if (pin == mostRecentUser?.pin) {
                        onPinCorrect()
                    } else {
                        isPinIncorrect = true
                    }
                }
            },
            label = { Text("Enter PIN") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPinValid || isPinIncorrect,
        )
        if (!isPinValid) {
            Text(
                text = "PIN must be exactly 4 digits",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
            )
        } else if (isPinIncorrect) {
            Text(
                text = "Incorrect PIN",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PinScreenPreview() {
    val userViewModel: UserViewModel = viewModel()
    PinScreen(
        userViewModel = userViewModel,
        onPinCorrect = {},
    )
}