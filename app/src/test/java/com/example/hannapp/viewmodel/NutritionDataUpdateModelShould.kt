package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.domain.DeleteNutritionUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.UpdateNutritionUseCase
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionDataUpdateModelShould {

    lateinit var nutritionDataUpdateViewModel: NutritionUpdateViewModel
    private val getNutritionUseCase = mock(GetNutritionUseCase::class.java)
    private val updateNutritionUseCase = mock(UpdateNutritionUseCase::class.java)
    private val deleteNutritionUseCase = mock(DeleteNutritionUseCase::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val nutritions = listOf(
        Nutrition(uid = 100, name = "Apple", kcal = "12kcal"),
        Nutrition(uid = 200, name = "Banana", kcal = "123kcal")
    )
    private val nutritionModels = listOf(
        NutritionModel(id = 100, name = "Apple", kcal = "12kcal"),
        NutritionModel(id = 200, name = "Banana", kcal = "123kcal")
    )

    private val pagingData = PagingData.from(nutritions)
    private val nutrimentsFlow = flowOf(pagingData)

    @BeforeEach
    fun beforeEach() = runTest {
        Dispatchers.setMain(testDispatcher)

        whenever(getNutritionUseCase.getAll()).thenReturn(
            nutrimentsFlow
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Nested
    inner class Instantiation {
        @Test
        fun emitsStateWithFetchedNutrimentsOnInstantiation() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                deleteNutritionUseCase = deleteNutritionUseCase,
                testDispatcher
            )

            verify(getNutritionUseCase).getAll()
            // TODO: Extension 'cachedIn' returns new instance of paging data with different memory reference than mocked paging data
            // Assertions.assertEquals(pagingData, nutritionDataUpdateViewModel.nutriments.first())
        }
    }

    @Nested
    inner class Select {

        @BeforeEach
        fun beforeEach() {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                deleteNutritionUseCase = deleteNutritionUseCase,
                testDispatcher
            )
        }

        @Test
        fun emitStateWithSelectedFoodNutrition() = runTest {
            Assertions.assertEquals(
                NutritionModel(),
                nutritionDataUpdateViewModel.uiState.value.nutritionModel
            )

            nutritionDataUpdateViewModel.selectItem(nutritionModels.last())

            Assertions.assertEquals(
                nutritionModels.last(),
                nutritionDataUpdateViewModel.uiState.value.nutritionModel
            )
        }
    }

    @Nested
    inner class ChangeOnCallback {

        @BeforeEach
        fun beforeEach() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                deleteNutritionUseCase = deleteNutritionUseCase,
                testDispatcher
            )
        }

        @Test
        fun changeUiStateOnCallback() {
            val updatedNutritionModel = NutritionModel(id = null, name = "Strawberry", kcal = "987cal")

            nutritionDataUpdateViewModel.onNutritionChange(Name(), "Strawberry")
            nutritionDataUpdateViewModel.onNutritionChange(Kcal(), "987cal")

            Assertions.assertEquals(
                updatedNutritionModel,
                nutritionDataUpdateViewModel.uiState.value.nutritionModel
            )
        }
    }

    @Nested
    inner class Updating {
        @BeforeEach
        fun beforeEach() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                deleteNutritionUseCase = deleteNutritionUseCase,
                testDispatcher
            )
        }

        @Test
        fun invokeUseCaseForUpdatingSelectedItem() = runTest {
            nutritionDataUpdateViewModel.selectItem(nutritionModels.last())

            nutritionDataUpdateViewModel.update()

            Assertions.assertEquals(
                nutritionModels.last(),
                nutritionDataUpdateViewModel.uiState.value.nutritionModel
            )
            verify(updateNutritionUseCase).invoke(nutritions.last())
        }

        @Test
        fun emitFailureStateOnFailingUpdate() = runTest {
            whenever(updateNutritionUseCase.invoke(any())).thenReturn(false)

            nutritionDataUpdateViewModel.update()

            Assertions.assertEquals(
                "Update failed",
                nutritionDataUpdateViewModel.uiState.value.errorMessage
            )
        }

        @Test
        fun emitFailureStateOnThrowingUpdate() = runTest {
            val errorMessage = "Internal Error"
            whenever(updateNutritionUseCase.invoke(any())).thenThrow(RuntimeException(errorMessage))

            nutritionDataUpdateViewModel.update()

            Assertions.assertEquals(
                errorMessage,
                nutritionDataUpdateViewModel.uiState.value.errorMessage
            )
        }
    }

    @Nested
    inner class Delete {

        @BeforeEach
        fun beforeEach() = runTest {
            nutritionDataUpdateViewModel = NutritionUpdateViewModel(
                getNutritionUseCase = getNutritionUseCase,
                updateNutritionUseCase = updateNutritionUseCase,
                deleteNutritionUseCase = deleteNutritionUseCase,
                testDispatcher
            )

            nutritionDataUpdateViewModel.nutritionConverter = NutritionConverter()
        }

        @Test
        fun invokeDeleteUseCase() = runTest {
            nutritionDataUpdateViewModel.delete(nutritionModels.last())

            verify(deleteNutritionUseCase).invoke(any())
        }
    }
}
