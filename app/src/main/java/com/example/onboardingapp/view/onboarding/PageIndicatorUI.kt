package com.example.onboardingapp.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * Composable function to display a page indicator for the onboarding screen.
 *
 * @param pageSize The total number of pages in the onboarding process.
 * @param currentPage The index of the currently selected page.
 * @param selectedColor The color of the indicator for the selected page. Defaults to the secondary color of the Material theme.
 * @param unselectedColor The color of the indicators for the unselected pages. Defaults to the secondary container color of the Material theme.
 */
@Composable
fun PageIndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.secondaryContainer,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        repeat(pageSize) {
            Spacer(
                modifier = Modifier
                    .size(2.dp)
            )

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (it == currentPage) 16.dp else 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = if (it == currentPage) selectedColor else unselectedColor)
            )

            Spacer(
                modifier = Modifier
                    .size(2.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview0() {
    PageIndicatorUI(
        pageSize = 6,
        currentPage = 0,
    )
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview2() {
    PageIndicatorUI(
        pageSize = 6,
        currentPage = 2,
    )
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview6() {
    PageIndicatorUI(
        pageSize = 6,
        currentPage = 5,
    )
}