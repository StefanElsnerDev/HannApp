package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.distinct.Fat
import com.example.hannapp.data.distinct.Kcal
import com.example.hannapp.data.distinct.Name
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import com.example.hannapp.domain.InsertNutritionUseCase
import com.example.hannapp.ui.viewmodel.ComponentUiState
import com.example.hannapp.ui.viewmodel.NutritionInsertViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Ignore
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
class NutritionInsertViewModelShould {

    lateinit var nutritionInsertViewModel: NutritionInsertViewModel
    private val insertNutritionUseCase = mock(InsertNutritionUseCase::class.java)
    private val getProductSearchResultsUseCase = mock(GetProductSearchResultsUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() = runTest {
        nutritionInsertViewModel = NutritionInsertViewModel(
            insertNutritionUseCase,
            getProductSearchResultsUseCase,
            testDispatcher
        )

        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateStateOnCallback() {
        val expectedNutritionState = NutritionUiModel(name = "Apple Juice", kcal = "12346.78")

        nutritionInsertViewModel.onNutritionChange(Name(), "Apple Juice")
        nutritionInsertViewModel.onNutritionChange(Kcal(), "12346.78")

        Assertions.assertEquals(
            expectedNutritionState,
            nutritionInsertViewModel.uiComponentState.value.nutritionUiModel
        )
    }

    @Test
    fun invokeInsertUseCase() = runTest {
        nutritionInsertViewModel.insert()

        verify(insertNutritionUseCase).invoke(any())
    }

    @Nested
    inner class ClearErrorState {

        @Test
        fun clearUiStateOnInsert() {
            val expectedUiState = ComponentUiState()

            nutritionInsertViewModel.onNutritionChange(
                Fat(),
                "123.4"
            )

            nutritionInsertViewModel.clearState()

            Assertions.assertEquals(expectedUiState, nutritionInsertViewModel.uiComponentState.value)
        }
    }

    @Nested
    inner class SearchResults {

        private val products = listOf(
            Product(
                code = "12345",
                productName = "Apple",
                nutriments = Nutriments(
                    1.1,
                    2.2,
                    3.3,
                    3.3,
                    4.4,
                    5.5,
                    6.6
                )
            ),
            Product(
                code = "6789",
                productName = "Apple Green",
                nutriments = Nutriments(
                    1.1,
                    2.2,
                    3.3,
                    3.3,
                    4.4,
                    5.5,
                    6.6
                )
            )
        )

        private val pagingData = PagingData.from(products)
        private val flow = flowOf(pagingData)

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(getProductSearchResultsUseCase.search(any(), any())).thenReturn(
                flow
            )
        }

        @Test
        fun callUseCase() = runTest {
            nutritionInsertViewModel.search("apple juice")

            verify(getProductSearchResultsUseCase).search(any(), any())
        }

        @Ignore("Due to 'cachedIn'-extension the receiving flow is internally manipulated and complicated to test")
        @Test
        fun fetchProductFlow() = runTest {
            nutritionInsertViewModel.search("apple juice")
        }

        @Test
        fun selectProduct() {
            val expectedNutritionUiModel = NutritionUiModel(
                id = null,
                name = "Delicious green Apple",
                kcal = "123.5",
                protein = "68.5",
                fat = "1.1",
                carbohydrates = "2.2",
                sugar = "3.3",
                fiber = "4.4",
                alcohol = "5.5"
            )

            val selectedProduct = Product(
                code = "123",
                productName = "Delicious green Apple",
                nutriments = Nutriments(123.5, 68.5, 1.1, 2.2, 3.3, 4.4, 5.5)
            )

            Assertions.assertEquals(
                NutritionUiModel(),
                nutritionInsertViewModel.uiComponentState.value.nutritionUiModel
            )

            nutritionInsertViewModel.select(selectedProduct)

            Assertions.assertEquals(
                expectedNutritionUiModel,
                nutritionInsertViewModel.uiComponentState.value.nutritionUiModel
            )
        }

        @Test
        fun validateOnProductSelection() {
            val selectedProduct = Product(
                code = "123",
                productName = "Delicious green Apple",
                nutriments = Nutriments(123.5, 68.5, 1.1, 2.2, 3.3, 4.4, 5.5)
            )

            Assertions.assertEquals(
                false,
                nutritionInsertViewModel.uiComponentState.value.isValid
            )

            nutritionInsertViewModel.select(selectedProduct)

            Assertions.assertEquals(
                true,
                nutritionInsertViewModel.uiComponentState.value.isValid
            )
        }
    }
}
