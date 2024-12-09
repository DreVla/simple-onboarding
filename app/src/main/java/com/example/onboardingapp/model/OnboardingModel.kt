package com.example.onboardingapp.model

/**
 * A sealed class representing the different screens in the onboarding process.
 *
 * @property title The title of the onboarding screen.
 * @property description The description of the onboarding screen.
 */
sealed class OnboardingModel(
//    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
    data object WelcomeScreen : OnboardingModel(
        title = "Welcome",
        description = "Well met! Let's start the onboarding process.",
    )

    data object TermsOfService : OnboardingModel(
        title = "Terms of Service",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
    )

    data object CredentialsScreen : OnboardingModel(
        title = "Credentials",
        description = "Please enter your email and password to continue.",
    )

    data object PersonalInfoScreen : OnboardingModel(
        title = "Personal Information",
        description = "Please provide your personal information to continue.",
    )

    data object NewPinScreen : OnboardingModel(
        title = "New PIN",
        description = "Please set a new PIN to continue.",
    )

    data object ConfirmPinScreen : OnboardingModel(
        title = "Confirm PIN",
        description = "Please confirm your PIN to continue.",
    )

    data object CompletedScreen : OnboardingModel(
        title = "Onboarding complete!",
        description = "Thank you for your time.",
    )
}