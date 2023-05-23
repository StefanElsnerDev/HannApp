package com.example.hannapp.viewmodel

import com.example.hannapp.data.distinct.Alcohol
import com.example.hannapp.data.distinct.Carbohydrates
import com.example.hannapp.data.distinct.Fat
import com.example.hannapp.data.distinct.Fiber
import com.example.hannapp.data.distinct.Kcal
import com.example.hannapp.data.distinct.Name
import com.example.hannapp.data.distinct.NutritionDataComponent
import com.example.hannapp.data.distinct.Protein
import com.example.hannapp.data.distinct.Sugar
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.viewmodel.ComponentUiState
import com.example.hannapp.ui.viewmodel.NutritionComponentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ComponentViewModelShould {

    class MockedComponentViewModel : NutritionComponentViewModel()

    lateinit var mockedComponentViewModel: MockedComponentViewModel

    @BeforeEach
    fun beforeEach() = runTest {
        mockedComponentViewModel = MockedComponentViewModel()
    }

    @Nested
    inner class ChangeOnCallback {

        @Test
        fun changeUiStateOnCallback() {
            val updatedNutritionUiModel =
                NutritionUiModel(id = null, name = "Strawberry", kcal = "987")

            mockedComponentViewModel.onNutritionChange(Name(), "Strawberry")
            mockedComponentViewModel.onNutritionChange(Kcal(), "987")

            Assertions.assertEquals(
                updatedNutritionUiModel,
                mockedComponentViewModel.uiComponentState.value.nutritionUiModel
            )
        }

        @Test
        fun emitUiStateOnEvent() {
            val expectedComponentUiState = NutritionUiModel(fat = "987.6")

            mockedComponentViewModel.onNutritionChange(
                Fat(),
                "987.6"
            )

            Assertions.assertEquals(expectedComponentUiState, mockedComponentViewModel.uiComponentState.value.nutritionUiModel)
        }

        @Test
        fun copyStateOnEvent() {
            val expectedComponentUiState = NutritionUiModel(
                fat = "123.4"
            )

            mockedComponentViewModel.onNutritionChange(
                Fat(),
                "123.4"
            )

            Assertions.assertEquals(expectedComponentUiState, mockedComponentViewModel.uiComponentState.value.nutritionUiModel)
        }
    }

    @Nested
    inner class Validate {

        @Test
        fun emitInvalidDataState() {
            mockedComponentViewModel.validate()
            Assertions.assertEquals(false, mockedComponentViewModel.uiComponentState.value.isValid)
        }

        @Test
        fun emitErrorUiState() {
            val expectedErrors = setOf(
                NutritionDataComponent.NAME,
                NutritionDataComponent.KCAL,
                NutritionDataComponent.PROTEIN,
                NutritionDataComponent.FAT,
                NutritionDataComponent.CARBOHYDRATES,
                NutritionDataComponent.SUGAR,
                NutritionDataComponent.FIBER,
                NutritionDataComponent.ALCOHOL
            )

            mockedComponentViewModel.validate()

            Assertions.assertEquals(expectedErrors, mockedComponentViewModel.uiComponentState.value.errors)
        }

        @Test
        fun emitValidDataState() {
            mockedComponentViewModel.fakeCompletion()

            mockedComponentViewModel.validate()

            Assertions.assertEquals(true, mockedComponentViewModel.uiComponentState.value.isValid)
        }

        private fun MockedComponentViewModel.fakeCompletion() {
            listOf(
                Name(),
                Kcal(),
                Protein(),
                Fat(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol()
            ).forEach {
                this.onNutritionChange(
                    it,
                    "123.4"
                )
            }
        }
    }

    @Nested
    inner class ResetErrors {
        @Test
        fun resetErrorOfComponent() {
            val expectedComponentUiState = ComponentUiState(
                errors = setOf(
                    NutritionDataComponent.KCAL,
                    NutritionDataComponent.PROTEIN,
                    NutritionDataComponent.FAT,
                    NutritionDataComponent.CARBOHYDRATES,
                    NutritionDataComponent.SUGAR,
                    NutritionDataComponent.FIBER,
                    NutritionDataComponent.ALCOHOL
                )
            )

            mockedComponentViewModel.validate()

            mockedComponentViewModel.resetError(NutritionDataComponent.NAME)

            Assertions.assertEquals(expectedComponentUiState, mockedComponentViewModel.uiComponentState.value)
        }
    }
}
