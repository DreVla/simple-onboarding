package com.example.onboardingapp.view.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable functions for various onboarding screens.
 */

/**
 * Displays the welcome screen with a title and description.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun WelcomeScreen(
    title: String,
    description: String,
    onProceed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

/**
 * Displays the terms of service screen with a title, description, and a checkbox to agree to the terms.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param isChecked The current state of the checkbox.
 * @param onIsCheckedChanged Callback function to be invoked when the checkbox state changes.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun TermsOfServiceScreen(
    title: String,
    description: String,
    isChecked: Boolean,
    onIsCheckedChanged: (Boolean) -> Unit,
    onProceed: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onIsCheckedChanged,
            )

            Spacer(
                modifier = Modifier
                    .width(8.dp),
            )

            Text(
                text = "I agree to the terms of service",
            )
        }

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$".toRegex()

/**
 * Displays the credentials screen with fields for email and password.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param email The current email input.
 * @param password The current password input.
 * @param onEmailChange Callback function to be invoked when the email input changes.
 * @param onPasswordChange Callback function to be invoked when the password input changes.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun CredentialsScreen(
    title: String,
    description: String,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onProceed: () -> Unit
) {
    var isEmailValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = email,
            onValueChange = {
                onEmailChange(it)
                isEmailValid = emailRegex.matches(it)
            },
            label = { Text("Email") },
            isError = !isEmailValid,
        )
        if (!isEmailValid) {
            Text(
                text = "Invalid email address",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

/**
 * Displays the personal information screen with fields for first name, last name, and phone number.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param firstName The current first name input.
 * @param lastName The current last name input.
 * @param phoneNumber The current phone number input.
 * @param onFirstNameChange Callback function to be invoked when the first name input changes.
 * @param onLastNameChange Callback function to be invoked when the last name input changes.
 * @param onPhoneNumberChange Callback function to be invoked when the phone number input changes.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun PersonalInfoScreen(
    title: String,
    description: String,
    firstName: String,
    lastName: String,
    phoneNumber: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onProceed: () -> Unit,
) {
    var isPhoneNumberValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("First Name") },
        )
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Last Name") },
        )

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = phoneNumber,
            onValueChange = {
                onPhoneNumberChange(it)
                isPhoneNumberValid = it.matches(Regex("^\\d{10}$"))
            },
            label = { Text("Phone Number") },
            isError = !isPhoneNumberValid,
        )
        if (!isPhoneNumberValid) {
            Text(
                text = "Phone number must be exactly 10 digits",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

/**
 * Displays the new PIN screen with a field to set a new PIN.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param pin The current PIN input.
 * @param onPinChanged Callback function to be invoked when the PIN input changes.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun NewPinScreen(
    title: String,
    description: String,
    pin: String,
    onPinChanged: (String) -> Unit,
    onProceed: (String) -> Unit,
) {
    var isPinValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = pin,
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    onPinChanged(it)
                    isPinValid = it.length == 4
                }
            },
            label = { Text("Set PIN") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPinValid,
        )
        if (!isPinValid) {
            Text(
                text = "PIN must be exactly 4 digits",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

/**
 * Displays the confirm PIN screen with a field to confirm the previously set PIN.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param setPin The previously set PIN.
 * @param confirmPin The current confirm PIN input.
 * @param onConfirmPinChanged Callback function to be invoked when the confirm PIN input changes.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun ConfirmPinScreen(
    title: String,
    description: String,
    setPin: String,
    confirmPin: String,
    onConfirmPinChanged: (String) -> Unit,
    onProceed: () -> Unit
) {
    var isSamePin by remember { mutableStateOf(true) }
    var isPinValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TextField(
            value = confirmPin,
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    onConfirmPinChanged(it)
                    isPinValid = it.length == 4
                    isSamePin = it == setPin
                } else {
                    isPinValid = false
                }
            },
            label = { Text("Confirm PIN") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isSamePin || !isPinValid
        )
        if (!isPinValid) {
            Text(
                text = "PIN must be exactly 4 digits",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        } else if (!isSamePin) {
            Text(
                text = "PINs do not match",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

/**
 * Displays the completed screen with a title and description.
 *
 * @param title The title text to display.
 * @param description The description text to display.
 * @param onProceed Callback function to be invoked when proceeding to the next screen.
 */
@Composable
fun CompletedScreen(
    title: String,
    description: String,
    onProceed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        TitleText(title)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )

        BodyText(description)

        Spacer(
            modifier = Modifier
                .size(32.dp),
        )
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
fun BodyText(bodyText: String) {
    Text(
        text = bodyText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        title = "Welcome",
        description = "Well met! Let's start the onboarding process."
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun TermsOfServiceScreenPreview() {
    TermsOfServiceScreen(
        title = "Terms of Service",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
        isChecked = false,
        onIsCheckedChanged = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun CredentialsScreenPreview() {
    CredentialsScreen(
        title = "Credentials",
        description = "Please enter your email and password to continue.",
        email = "",
        password = "",
        onEmailChange = {},
        onPasswordChange = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun PersonalInfoScreenPreview() {
    PersonalInfoScreen(
        title = "Personal Information",
        description = "Please provide your personal information to continue.",
        firstName = "",
        lastName = "",
        phoneNumber = "",
        onFirstNameChange = {},
        onLastNameChange = {},
        onPhoneNumberChange = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun NewPinScreenPreview() {
    NewPinScreen(
        title = "New PIN",
        description = "Please set a new PIN to continue.",
        pin = "",
        onPinChanged = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun ConfirmPinScreenPreview() {
    ConfirmPinScreen(
        title = "Confirm PIN",
        description = "Please confirm your PIN to continue.",
        setPin = "1234",
        confirmPin = "",
        onConfirmPinChanged = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun CompletedScreenPreview() {
    WelcomeScreen(
        title = "Onboarding Complete!",
        description = "Thank you for your time."
    ) {

    }
}