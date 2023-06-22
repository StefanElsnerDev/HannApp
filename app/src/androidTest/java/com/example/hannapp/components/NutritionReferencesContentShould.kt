package com.example.hannapp.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.hannapp.R
import com.example.hannapp.ui.components.NutritionReferencesContent
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NutritionReferencesContentShould {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val kcalValue = "100.5"
    private val proteinValue = "abd"
    private val carbohydratesValue = "50.50"
    private val fatValue = "0.50"

    private val kcalState = NutritionLimitContract.ReferenceState.State(
        value = kcalValue,
        isError = false
    )

    private val proteinState = NutritionLimitContract.ReferenceState.State(
        value = proteinValue,
        isError = true
    )

    private val carbohydratesState = NutritionLimitContract.ReferenceState.State(
        value = carbohydratesValue,
        isError = false
    )

    private val fatState = NutritionLimitContract.ReferenceState.State(
        value = fatValue,
        isError = false
    )

    @Before
    fun before() {
        composeTestRule.setContent {
            HannAppTheme {
                NutritionReferencesContent(
                    kcalState = kcalState,
                    proteinState = proteinState,
                    carbohydratesState = carbohydratesState,
                    fatState = fatState,
                    event = {}
                )
            }
        }
    }

    @Test
    fun hasHeader() {
        val header = composeTestRule.activity.getString(R.string.nutrition_references)

        composeTestRule.onNodeWithText(header).assertIsDisplayed()
    }

    @Test
    fun hasQuantityFieldsWithLabel() {
        val energy = composeTestRule.activity.getString(R.string.energy)
        val protein = composeTestRule.activity.getString(R.string.protein)
        val carbohydrates = composeTestRule.activity.getString(R.string.carbohydrates)
        val fat = composeTestRule.activity.getString(R.string.fat)

        composeTestRule.onNodeWithText(energy)
            .assertIsDisplayed()
            .assert(hasText(kcalValue))

        composeTestRule.onNodeWithText(protein)
            .assertIsDisplayed()
            .assert(hasText(proteinValue))

        composeTestRule.onNodeWithText(carbohydrates)
            .assertIsDisplayed()
            .assert(hasText(carbohydratesValue))

        composeTestRule.onNodeWithText(fat)
            .assertIsDisplayed()
            .assert(hasText(fatValue))
    }

    @Test
    fun displaysErrorAndHint() {
        val protein = composeTestRule.activity.getString(R.string.protein)
        val errorMessage = composeTestRule.activity.getString(R.string.fill_field)

        composeTestRule.onNodeWithText(protein).assert(hasText(errorMessage))
    }
}
