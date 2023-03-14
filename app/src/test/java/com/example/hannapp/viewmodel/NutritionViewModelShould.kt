package com.example.hannapp.viewmodel

import com.example.hannapp.domain.GetNutritionBMIsUseCase
import com.example.hannapp.ui.viewmodel.NutritionUiState
import com.example.hannapp.ui.viewmodel.NutritionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionViewModelShould {

    private lateinit var nutritionViewModel: NutritionViewModel
    private val getNutritionBMIsUseCase = mock(GetNutritionBMIsUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val nutritionNames = listOf("Apple", "Banana", "Grapefruit")


    @BeforeEach
    fun beforeEach() {
        whenever(getNutritionBMIsUseCase.invoke()).thenReturn(
            flowOf(nutritionNames)
        )

        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeUseCaseOnInstantiation() = runTest {
        nutritionViewModel = NutritionViewModel(
            getNutritionBMIsUseCase,
            testDispatcher
        )

        verify(getNutritionBMIsUseCase).invoke()
    }

    @Test
    fun transformDataToUIStateOnInstantiation() = runTest {
        nutritionViewModel = NutritionViewModel(
            getNutritionBMIsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = null,
                nutritionNames = nutritionNames
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Test
    fun produceUIStateWithEmptyData() = runTest {
        whenever(getNutritionBMIsUseCase.invoke()).thenReturn(
            flowOf(null)
        )

        nutritionViewModel = NutritionViewModel(
            getNutritionBMIsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = null,
                nutritionNames = emptyList()
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Test
    fun produceErrorUIStateOnException() = runTest {
        val errorMessage = "error"
        whenever(getNutritionBMIsUseCase.invoke()).thenReturn(
            flow {
                throw RuntimeException(errorMessage)
            }
        )

        nutritionViewModel = NutritionViewModel(
            getNutritionBMIsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = errorMessage,
                nutritionNames = emptyList()
            ),
            nutritionViewModel.uiState.first()
        )
    }
}
