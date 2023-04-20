package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionSelectViewModelShould {

    private lateinit var nutritionViewModel: NutritionSelectViewModel
    private val getNutritionUseCase = mock(GetNutritionUseCase::class.java)
    private val insertNutrimentLogUseCase = mock(InsertNutrimentLogUseCase::class.java)
    private val getNutrimentLogUseCase = mock(GetNutrimentLogUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val nutritions = listOf(
        Nutrition(uid = 100, name = "Apple", kcal = 1.2),
        Nutrition(uid = 200, name = "Banana", kcal = 3.4)
    )

    private val pagingData = PagingData.from(nutritions)
    private val nutrimentsFlow = flowOf(pagingData)

    private val nutrimentLog = listOf(
        NutrimentLogModel(
            nutrition = Nutrition(
                uid = 123
            ),
            quantity = 1.1,
            createdAt = 1681839531,
            modifiedAt = null
        ),
        NutrimentLogModel(
            nutrition = Nutrition(
                uid = 456
            ),
            quantity = 2.2,
            createdAt = 1681234731,
            modifiedAt = null
        )
    )

    private val nutrimentUiLog = listOf(
        NutrimentUiLogModel(
            nutrition = NutritionUiModel(
                id = 123
            ),
            quantity = 1.1,
            unit = "",
            timeStamp = 1681839531
        ),
        NutrimentUiLogModel(
            nutrition = NutritionUiModel(
                id = 456
            ),
            quantity = 2.2,
            unit = "",
            timeStamp = 1681234731
        )
    )

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(testDispatcher)

        whenever(getNutritionUseCase.getAll()).thenReturn(
            nutrimentsFlow
        )

        whenever(getNutrimentLogUseCase.observeNutrimentLog()).thenReturn(
            flowOf(nutrimentLog)
        )

        nutritionViewModel = NutritionSelectViewModel(
            getNutritionUseCase,
            insertNutrimentLogUseCase,
            getNutrimentLogUseCase,
            testDispatcher
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeGetNutritionUseCaseOn() = runTest {
        nutritionViewModel.getAll()

        verify(getNutritionUseCase).getAll()
    }

    @Test
    fun produceUIStateWithLoadingStateOnInstantiation() = runTest {
        nutritionViewModel = NutritionSelectViewModel(
            getNutritionUseCase,
            insertNutrimentLogUseCase,
            getNutrimentLogUseCase,
            testDispatcher
        )

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = true,
                errorMessage = null
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Test
    fun produceErrorUIStateOnException() = runTest {
        val errorMessage = "error"
        whenever(getNutritionUseCase.getAll()).thenReturn(
            flow {
                throw RuntimeException(errorMessage)
            }
        )

        nutritionViewModel.getAll()

        Assertions.assertEquals(
            NutritionUiState(
                isLoading = false,
                errorMessage = errorMessage
            ),
            nutritionViewModel.uiState.first()
        )
    }

    @Nested
    inner class NutrimentLogFlow {

        @Test
        fun emitsNutrimentLog() = runTest {
            assertThat(nutritionViewModel.nutrimentLog.first()).isEqualTo(nutrimentUiLog)
        }

        @Test
        fun emitsErrorStateOnFailingNutrimentLog() = runTest {
            val errorMessage = "Error"
            whenever(getNutrimentLogUseCase.observeNutrimentLog()).thenReturn(
                flow {
                    throw RuntimeException(errorMessage)
                }
            )

            nutritionViewModel = NutritionSelectViewModel(
                getNutritionUseCase,
                insertNutrimentLogUseCase,
                getNutrimentLogUseCase,
                testDispatcher
            )

            assertThat(nutritionViewModel.uiState.value.errorMessage).contains(errorMessage)
        }
    }

    @Nested
    inner class AddToLog {

        private val nutritionUiModel = NutritionUiModel(
            name = "Cola",
            kcal = "123",
            protein = "0.1",
            fat = "0.0"
        )

        @Test
        fun callsUseCaseForAddingNutriment() = runTest {
            val quantity = 56.78

            nutritionViewModel.add(nutritionUiModel = nutritionUiModel, quantity = quantity)

            verify(insertNutrimentLogUseCase).invoke(any())
        }

        @Test
        fun emitsErrorStateOnFailingInsertion() = runTest {
            whenever(insertNutrimentLogUseCase.invoke(any())).thenReturn(false)

            assertThat(nutritionViewModel.uiState.value.errorMessage).isNull()

            nutritionViewModel.add(NutritionUiModel(), 1.23)

            assertThat(nutritionViewModel.uiState.value.errorMessage).isNotBlank()
        }

        @Test
        fun emitsErrorStateOnThrowingInsertion() = runTest {
            val errorMessage = "Insertion failed!"
            whenever(insertNutrimentLogUseCase.invoke(any())).thenThrow(
                RuntimeException(
                    errorMessage
                )
            )

            nutritionViewModel.add(NutritionUiModel(), 1.23)

            assertThat(nutritionViewModel.uiState.value.errorMessage).isEqualTo(errorMessage)
        }
    }
}
