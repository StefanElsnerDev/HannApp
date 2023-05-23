package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.repository.NutritionRepository
import com.example.hannapp.domain.InsertNutritionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class InsertNutritionUseCaseShould {

    private lateinit var insertNutritionUseCase: InsertNutritionUseCase
    private val nutritionRepository: NutritionRepository = mock(NutritionRepository::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(testDispatcher)

        insertNutritionUseCase = InsertNutritionUseCase(
            nutritionRepository
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeInsertOfRepository() = runTest {
        insertNutritionUseCase.invoke(NutritionUiModel())

        verify(nutritionRepository).insert(any())
    }
}
