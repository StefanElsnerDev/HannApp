package com.example.hannapp.viewmodel

import com.example.hannapp.data.distinct.Energy
import com.example.hannapp.data.distinct.Fad
import com.example.hannapp.domain.InsertNutritionUseCase
import com.example.hannapp.ui.viewmodel.NutritionComponentState
import com.example.hannapp.ui.viewmodel.NutritionDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionDataViewModelShould {

    lateinit var nutritionDataViewModel: NutritionDataViewModel
    private val insertNutritionUseCase = mock(InsertNutritionUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() = runTest {
        nutritionDataViewModel = NutritionDataViewModel(
            insertNutritionUseCase,
            testDispatcher
        )

        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeInsertUseCase() = runTest {
        nutritionDataViewModel.insert()

        verify(insertNutritionUseCase).invoke(any())
    }

    @Test
    fun emitUiStateOnEvent() {
        val expectedUiState = NutritionComponentState(energy = "987.6")

        nutritionDataViewModel.onNutritionTypeChange(
            Energy(), "987.6"
        )

        Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
    }

    @Test
    fun copyStateOnEvent() {
        val expectedUiState = NutritionComponentState(
            fad = "123.4",
            energy = "987.6"
        )

        nutritionDataViewModel.onNutritionTypeChange(
            Energy(), "987.6"
        )
        nutritionDataViewModel.onNutritionTypeChange(
            Fad(), "123.4"
        )

        Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
    }
}
