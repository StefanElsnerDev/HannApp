package com.example.hannapp.viewmodel

import com.example.hannapp.data.model.Food
import com.example.hannapp.domain.GetNutritionsUseCase
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
    private val getNutritionsUseCase = mock(GetNutritionsUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val foodList = listOf(Food(1, "Apple"), Food(2, "Banana"), Food(3, "Grapefruit"))

    @BeforeEach
    fun beforeEach() {
        whenever(getNutritionsUseCase.invoke()).thenReturn(
            flowOf(foodList)
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
            getNutritionsUseCase,
            testDispatcher
        )

        verify(getNutritionsUseCase).invoke()
    }

    @Test
    fun transformDataToUIStateOnInstantiation() = runTest {
        nutritionViewModel = NutritionViewModel(
            getNutritionsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = null,
                foodList = foodList
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Test
    fun produceUIStateWithEmptyData() = runTest {
        whenever(getNutritionsUseCase.invoke()).thenReturn(
            flowOf(null)
        )

        nutritionViewModel = NutritionViewModel(
            getNutritionsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = null,
                foodList = emptyList()
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Test
    fun produceErrorUIStateOnException() = runTest {
        val errorMessage = "error"
        whenever(getNutritionsUseCase.invoke()).thenReturn(
            flow {
                throw RuntimeException(errorMessage)
            }
        )

        nutritionViewModel = NutritionViewModel(
            getNutritionsUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = errorMessage,
                foodList = emptyList()
            ),
            nutritionViewModel.uiState.first()
        )
    }
}
