package com.example.hannapp.viewmodel

import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.domain.GetFoodUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.UpdateNutritionUseCase
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionDataUpdateModelShould {

    lateinit var nutritionDataUpdateViewModel: NutritionUpdateViewModel
    private val getFoodUseCase = mock(GetFoodUseCase::class.java)
    private val getNutritionUseCase = mock(GetNutritionUseCase::class.java)
    private val updateNutritionUseCase = mock(UpdateNutritionUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val food = listOf(Food(100, "Apple"), Food(200, "Banana"))
    private val nutritions = listOf(
        Nutrition(uid = 100, name = "Apple", kcal = "12kcal"),
        Nutrition(uid = 200, name = "Banana", kcal = "123kcal")
    )

    @BeforeEach
    fun beforeEach() = runTest {
        Dispatchers.setMain(testDispatcher)

        whenever(getFoodUseCase.invoke()).thenReturn(
            flowOf(food)
        )
        whenever(getNutritionUseCase.invoke(100)).thenReturn(nutritions.first())

    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Nested
    inner class Instantiation {
        @Test
        fun emitsStateWithFetchedFoodOnInstantiation() {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getFoodUseCase = getFoodUseCase,
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                testDispatcher
            )

            verify(getFoodUseCase).invoke()
            Assertions.assertEquals(food, nutritionDataUpdateViewModel.uiState.value.foodList)
        }

        @Test
        fun emitPreselectedNutritionDataState() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getFoodUseCase = getFoodUseCase,
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                testDispatcher
            )

            verify(getNutritionUseCase).invoke(100)
            Assertions.assertEquals(
                nutritions.first(),
                nutritionDataUpdateViewModel.uiState.value.nutrition
            )
        }
    }

    @Nested
    inner class Select {

        @BeforeEach
        fun beforeEach() {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getFoodUseCase = getFoodUseCase,
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                testDispatcher
            )
        }

        @Test
        fun invokeUseCaseToFetchSelectedFoodNutrition() = runTest {
            nutritionDataUpdateViewModel.selectItem(1)

            verify(getNutritionUseCase).invoke(200)
        }

        @Test
        fun emitStateWithSelectedFoodNutrition() = runTest {
            whenever(getNutritionUseCase.invoke(200)).thenReturn(nutritions.last())

            nutritionDataUpdateViewModel.selectItem(1)

            Assertions.assertEquals(
                nutritions.last(),
                nutritionDataUpdateViewModel.uiState.value.nutrition
            )
        }
    }

    @Nested
    inner class ChangeOnCallback {

        @BeforeEach
        fun beforeEach() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getFoodUseCase = getFoodUseCase,
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                testDispatcher
            )
        }

        @Test
        fun changeUiStateOnCallback() {
            val updatedNutrition = Nutrition(uid = 100, name = "Strawberry", kcal = "987cal")

            nutritionDataUpdateViewModel.onNutritionTypeChange(Name(), "Strawberry")
            nutritionDataUpdateViewModel.onNutritionTypeChange(Kcal(), "987cal")

            Assertions.assertEquals(
                updatedNutrition,
                nutritionDataUpdateViewModel.uiState.value.nutrition
            )
        }
    }
}
