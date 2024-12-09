package com.example.onboardingapp.view.onboarding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable function that displays a customizable button.
 *
 * @param text The text to display on the button.
 * @param backgroundColor The background color of the button. Defaults to the primary color of the theme.
 * @param textColor The color of the text on the button. Defaults to the onPrimary color of the theme.
 * @param textStyle The style of the text on the button. Defaults to the titleMedium typography style of the theme.
 * @param fontSize The size of the text on the button in sp. Defaults to 14.
 * @param enabled Whether the button is enabled or not. Defaults to true.
 * @param onClick The callback function to be invoked when the button is clicked.
 */
@Composable
fun ButtonUI(
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    fontSize: Int = 14,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    var buttonColors: ButtonColors
    if (!enabled) {
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.White,
        )
    } else {
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
        )
    }

    Button(
        onClick = {
            if (enabled) {
                onClick()
            }
        },
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            style = textStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProceedButtonPreview() {
    ButtonUI(text = "Proceed") { }
}

@Preview(showBackground = true)
@Composable
fun ProceedButtonDisabledPreview() {
    ButtonUI(
        text = "Proceed",
        enabled = false,
    ) { }
}

@Preview(showBackground = true)
@Composable
fun BackButtonPreview() {
    ButtonUI(
        text = "Back",
        backgroundColor = Color.Transparent,
        textColor = Color.Gray,
        textStyle = MaterialTheme.typography.bodySmall,
        fontSize = 14,
    ) { }
}