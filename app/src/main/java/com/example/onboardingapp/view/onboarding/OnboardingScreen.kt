package com.example.onboardingapp.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onboardingapp.model.OnboardingModel
import com.example.onboardingapp.model.UserData
import com.example.onboardingapp.view.onboarding.screens.CompletedScreen
import com.example.onboardingapp.view.onboarding.screens.ConfirmPinScreen
import com.example.onboardingapp.view.onboarding.screens.CredentialsScreen
import com.example.onboardingapp.view.onboarding.screens.NewPinScreen
import com.example.onboardingapp.view.onboarding.screens.PersonalInfoScreen
import com.example.onboardingapp.view.onboarding.screens.TermsOfServiceScreen
import com.example.onboardingapp.view.onboarding.screens.WelcomeScreen
import com.example.onboardingapp.view.onboarding.screens.emailRegex
import com.example.onboardingapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    userViewModel: UserViewModel,
    onFinished: () -> Unit,
) {
    val onboardingPages = listOf(
        OnboardingModel.WelcomeScreen,
        OnboardingModel.TermsOfService,
        OnboardingModel.CredentialsScreen,
        OnboardingModel.PersonalInfoScreen,
        OnboardingModel.NewPinScreen,
        OnboardingModel.ConfirmPinScreen,
        OnboardingModel.CompletedScreen,
    )

    var currentPage by rememberSaveable { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = currentPage) {
        onboardingPages.size
    }

    val scope = rememberCoroutineScope()

    var isChecked by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }
    var confirmPin by rememberSaveable { mutableStateOf("") }

    var canProceed by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (pagerState.currentPage != 0) {
                        ButtonUI(
                            text = "Back",
                            backgroundColor = Color.Transparent,
                            textColor = Color.Gray,
                            textStyle = MaterialTheme.typography.bodySmall,
                            fontSize = 14,
                        ) {
                            canProceed = when (onboardingPages[pagerState.currentPage - 1]) {
                                is OnboardingModel.WelcomeScreen -> true
                                is OnboardingModel.TermsOfService -> isChecked
                                is OnboardingModel.CredentialsScreen -> email.isNotEmpty() && password.isNotEmpty()
                                is OnboardingModel.PersonalInfoScreen -> firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()
                                is OnboardingModel.NewPinScreen -> pin.isNotEmpty()
                                is OnboardingModel.ConfirmPinScreen -> confirmPin.isNotEmpty()
                                else -> true
                            }

                            scope.launch {
                                if (pagerState.currentPage > 0) {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    currentPage = pagerState.currentPage - 1
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    PageIndicatorUI(
                        pageSize = onboardingPages.size,
                        currentPage = pagerState.currentPage,
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    ButtonUI(
                        text = "Proceed",
                        enabled = canProceed,
                    ) {

                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                currentPage = pagerState.currentPage + 1
                            }
                            canProceed = when (onboardingPages[pagerState.currentPage + 1]) {
                                is OnboardingModel.WelcomeScreen -> true
                                is OnboardingModel.TermsOfService -> isChecked
                                is OnboardingModel.CredentialsScreen -> email.isNotEmpty() && password.isNotEmpty()
                                is OnboardingModel.PersonalInfoScreen -> firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()
                                is OnboardingModel.NewPinScreen -> pin.isNotEmpty()
                                is OnboardingModel.ConfirmPinScreen -> confirmPin.isNotEmpty()
                                is OnboardingModel.CompletedScreen -> true
                                else -> true
                            }
                        } else {
                            val userData = UserData(
                                email = email,
                                password = password,
                                firstName = firstName,
                                lastName = lastName,
                                phoneNumber = phoneNumber,
                                pin = pin,
                            )
                            userViewModel.insert(userData)
                            onFinished()
                        }
                    }
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
            ) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                ) { index ->
                    when (onboardingPages[index]) {
                        is OnboardingModel.WelcomeScreen -> WelcomeScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.TermsOfService -> TermsOfServiceScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description,
                            isChecked = isChecked,
                            onIsCheckedChanged = { isChecked = it; canProceed = it },
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.CredentialsScreen -> CredentialsScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description,
                            email = email,
                            password = password,
                            onEmailChange = {
                                email = it;
                                canProceed =
                                    email.isNotBlank() && password.isNotBlank() && emailRegex.matches(
                                        email
                                    )
                            },
                            onPasswordChange = {
                                password = it;
                                canProceed =
                                    email.isNotBlank() && password.isNotBlank() && emailRegex.matches(
                                        email
                                    )
                            },
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.PersonalInfoScreen -> PersonalInfoScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description,
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                            onFirstNameChange = {
                                firstName = it;
                                canProceed =
                                    firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.matches(
                                        Regex("^\\d{10}$")
                                    )
                            },
                            onLastNameChange = {
                                lastName = it;
                                canProceed =
                                    firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.matches(
                                        Regex("^\\d{10}$")
                                    )
                            },
                            onPhoneNumberChange = {
                                phoneNumber = it;
                                canProceed =
                                    firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.matches(
                                        Regex("^\\d{10}$")
                                    )
                            },
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.NewPinScreen -> NewPinScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description,
                            pin = pin,
                            onPinChanged = {
                                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                    pin = it
                                    canProceed = pin.length == 4
                                }
                            },
                        ) { enteredPin ->
                            pin = enteredPin
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.ConfirmPinScreen -> ConfirmPinScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description,
                            setPin = pin,
                            confirmPin = confirmPin,
                            onConfirmPinChanged = {
                                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                    confirmPin = it
                                    canProceed = confirmPin.length == 4 && confirmPin == pin
                                }
                            },
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }

                        is OnboardingModel.CompletedScreen -> CompletedScreen(
                            title = onboardingPages[index].title,
                            description = onboardingPages[index].description
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            currentPage = index + 1
                        }
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    val userViewModel: UserViewModel = viewModel()
    OnboardingScreen(userViewModel) { }
}