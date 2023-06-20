package com.example.hannapp.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.hannapp.R
import com.example.hannapp.ui.components.MilkQuantityContent
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MilkQuantityContentShould {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val total = NutritionLimitContract.ReferenceState.State(
        value = "100.5",
        isError = false
    )

    private val preNight = NutritionLimitContract.ReferenceState.State(
        value = "abd",
        isError = true
    )

    private val night = NutritionLimitContract.ReferenceState.State(
        value = "50.50",
        isError = false
    )

    @Before
    fun before() {
        composeTestRule.setContent {
            HannAppTheme {
                MilkQuantityContent(
                    totalState = total,
                    nightState = night,
                    preNightState = preNight,
                    event = {}
                )
            }
        }
    }

    @Test
    fun hasHeader() {
        composeTestRule.onNodeWithText("Milk quantities").assertIsDisplayed()
    }

    @Test
    fun hasQuantityFieldsWithLabel() {
        composeTestRule.onNodeWithText("Total")
            .assertIsDisplayed()
            .assert(hasText("100.5"))

        composeTestRule.onNodeWithText("Share 8:30 PM")
            .assertIsDisplayed()
            .assert(hasText("abd"))

        composeTestRule.onNodeWithText("Share Night")
            .assertIsDisplayed()
            .assert(hasText("50.50"))
    }

    @Test
    fun displaysErrorAndHint() {
        val errorMessage = composeTestRule.activity.getString(R.string.fill_field)

        composeTestRule.onNodeWithText("Share 8:30 PM").assert(hasText(errorMessage))
    }
}
