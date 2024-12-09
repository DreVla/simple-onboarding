package com.example.onboardingapp.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onboardingapp.view.onboarding.ButtonUI
import com.example.onboardingapp.viewmodel.UserViewModel

@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    onSignOut: () -> Unit,
) {
    val mostRecentUser by userViewModel.getMostRecentUser().observeAsState()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonUI(
                    text = "Sign Out",
                    backgroundColor = Color.Red,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    textColor = Color.White,
                    fontSize = 14,
                ) {
                    onSignOut()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(36.dp),
                    text = "Greetings \n ${mostRecentUser?.firstName} ${mostRecentUser?.lastName}!",
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val userViewModel: UserViewModel = viewModel()
    MainScreen(userViewModel) { }
}