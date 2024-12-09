package com.example.onboardingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onboardingapp.ui.theme.OnboardingAppTheme
import com.example.onboardingapp.utils.SharedPrefUtils
import com.example.onboardingapp.view.MainScreen
import com.example.onboardingapp.view.PinScreen
import com.example.onboardingapp.view.onboarding.OnboardingScreen
import com.example.onboardingapp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnboardingAppTheme {
                val context = LocalContext.current
                val isSignedIn = SharedPrefUtils.getIsSignedIn(LocalContext.current)

                val navController = rememberNavController()
                val startDestination = if (isSignedIn) "PinScreen" else "OnboardingScreen"
                NavHost(navController = navController, startDestination = startDestination) {

                    composable(route = "MainScreen") {
                        ShowMainScreen(context, userViewModel, navController)
                    }
                    composable(route = "OnboardingScreen") {
                        ShowOnboardingScreen(context, userViewModel, navController)
                    }
                    composable(route = "PinScreen") {
                        ShowPinScreen(context, userViewModel, navController)
                    }


                }
            }
        }
    }
}

@Composable
private fun ShowOnboardingScreen(
    context: Context,
    userViewModel: UserViewModel = viewModel(),
    navController: NavHostController,
) {
    OnboardingScreen(
        userViewModel = userViewModel,
    ) {
        navController.navigate("MainScreen")
        SharedPrefUtils.saveIsSignedIn(context, true)
    }
}

@Composable
private fun ShowMainScreen(
    context: Context,
    userViewModel: UserViewModel = viewModel(),
    navController: NavHostController,
) {
    MainScreen(
        userViewModel = userViewModel,
        onSignOut = {
            SharedPrefUtils.saveIsSignedIn(context, false)
            navController.navigate("OnboardingScreen")
        })
}

@Composable
private fun ShowPinScreen(
    context: Context,
    userViewModel: UserViewModel,
    navController: NavHostController,
) {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        ),
    ) {
        PinScreen(
            userViewModel = userViewModel,
            onPinCorrect = {
                // Here you can add logic to verify the PIN if needed
                navController.navigate("MainScreen")
            },
        )
    }
}