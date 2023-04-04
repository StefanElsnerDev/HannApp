package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import com.example.hannapp.domain.InsertNutritionUseCase
import com.example.hannapp.ui.viewmodel.NutritionInsertState
import com.example.hannapp.ui.viewmodel.NutritionInsertViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Ignore
import org.junit.jupiter.api.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionInsertViewModelShould {

    lateinit var nutritionDataViewModel: NutritionInsertViewModel
    private val insertNutritionUseCase = mock(InsertNutritionUseCase::class.java)
    private val getProductSearchResultsUseCase = mock(GetProductSearchResultsUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() = runTest {
        nutritionDataViewModel = NutritionInsertViewModel(
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
        val expectedNutritionState = NutritionModel(name = "Apple Juice", kcal = "12346.78")

        nutritionDataViewModel.onNutritionChange(Name(), "Apple Juice")
        nutritionDataViewModel.onNutritionChange(Kcal(), "12346.78")

        Assertions.assertEquals(
            expectedNutritionState,
            nutritionDataViewModel.uiState.value.nutrition
        )
    }

    @Test
    fun invokeInsertUseCase() = runTest {
        nutritionDataViewModel.insert()

        verify(insertNutritionUseCase).invoke(any())
    }

    @Test
    fun emitUiStateOnEvent() {
        val expectedUiState = NutritionInsertState(nutrition = NutritionModel(fat = "987.6"))

        nutritionDataViewModel.onNutritionChange(
            Fat(), "987.6"
        )

        Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
    }

    @Test
    fun copyStateOnEvent() {
        val expectedUiState = NutritionInsertState(nutrition = NutritionModel(
            fat = "123.4"
        ))

        nutritionDataViewModel.onNutritionChange(
            Fat(), "123.4"
        )

        Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
    }

    @Nested
    inner class ErrorState{

        @Test
        fun emitErrorUiState(){
            val expectedUiState = NutritionInsertState(
                errors = setOf(
                    NutritionDataComponent.NAME,
                    NutritionDataComponent.KCAL,
                    NutritionDataComponent.PROTEIN,
                    NutritionDataComponent.FAT,
                    NutritionDataComponent.CARBOHYDRATES,
                    NutritionDataComponent.SUGAR,
                    NutritionDataComponent.FIBER,
                    NutritionDataComponent.ALCOHOL
                )
            )

            nutritionDataViewModel.validate()

            Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
        }

        @Test
        fun resetErrorOfComponent() {
            val expectedUiState = NutritionInsertState(
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

            nutritionDataViewModel.validate()

            nutritionDataViewModel.resetError(NutritionDataComponent.NAME)

            Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
        }

        @Test
        fun emitInvalidDataState() {
            nutritionDataViewModel.validate()
            Assertions.assertEquals(false, nutritionDataViewModel.uiState.value.isValid)
        }

        @Test
        fun emitValidDataState() {
            nutritionDataViewModel.fakeCompletion()

            nutritionDataViewModel.validate()

            Assertions.assertEquals(true, nutritionDataViewModel.uiState.value.isValid)
        }

        @Test
        fun clearUiStateOnInsert() {
            val expectedUiState = NutritionInsertState()

            nutritionDataViewModel.onNutritionChange(
                Fat(), "123.4"
            )

            nutritionDataViewModel.clearState()

            Assertions.assertEquals(expectedUiState, nutritionDataViewModel.uiState.value)
        }

        private fun NutritionInsertViewModel.fakeCompletion(){
            listOf(
                Name(),
                Kcal(),
                Protein(),
                Fat(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol()
            ).forEach{
                this.onNutritionChange(
                    it, "String"
                )
            }
        }
    }

    @Nested
    inner class SearchResults{

        private val products = listOf(
            Product(
                code = "12345",
                productName = "Apple",
                nutriments = Nutriments(
                    1.1, 2.2, 3.3, 3.3, 4.4, 5.5, 6.6
                )
            ),
            Product(
                code = "6789",
                productName = "Apple Green",
                nutriments = Nutriments(
                    1.1, 2.2, 3.3, 3.3, 4.4, 5.5, 6.6
                )
            )
        )

        private val pagingData = PagingData.from(products)
        private val flow = flowOf(pagingData)

        @BeforeEach
        fun beforeEach() = runTest{
            whenever(getProductSearchResultsUseCase.search(any(), any())).thenReturn(
                flow
            )
        }

        @Test
        fun callUseCase() = runTest {
            nutritionDataViewModel.search("apple juice")

            verify(getProductSearchResultsUseCase).search(any(), any())
        }

        @Ignore("Due to 'cachedIn'-extension the receiving flow is internally manipulated and complicated to test")
        @Test
        fun fetchProductFlow() = runTest {
            nutritionDataViewModel.search("apple juice")

        }

        @Test
        fun selectProduct(){
            val expectedNutritionModel = NutritionModel(
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
                NutritionModel(),
                nutritionDataViewModel.uiState.value.nutrition
            )

            nutritionDataViewModel.select(selectedProduct)

            Assertions.assertEquals(
                expectedNutritionModel,
                nutritionDataViewModel.uiState.value.nutrition
            )
        }

        @Test
        fun validateOnProductSelection(){
            val selectedProduct = Product(
                code = "123",
                productName = "Delicious green Apple",
                nutriments = Nutriments(123.5, 68.5, 1.1, 2.2, 3.3, 4.4, 5.5)
            )

            Assertions.assertEquals(
                false,
                nutritionDataViewModel.uiState.value.isValid
            )

            nutritionDataViewModel.select(selectedProduct)

            Assertions.assertEquals(
                true,
                nutritionDataViewModel.uiState.value.isValid
            )
        }
    }
}
