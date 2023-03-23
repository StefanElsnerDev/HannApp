package com.example.hannapp.usecase

import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import com.example.hannapp.domain.GetNutritionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNutritionUseCaseShould {

    lateinit var getNutritionUseCase: GetNutritionUseCase
    private val nutritionRepository: NutritionRepository = mock(NutritionRepository::class.java)
    private val nutrition = Nutrition(uid = 123, name = "Sugar")

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutritionRepository.get(123)).thenReturn(nutrition)

        getNutritionUseCase = GetNutritionUseCase(
            nutritionRepository
        )
    }

    @Test
    fun invokeGetterOfRepository() = runTest {
        getNutritionUseCase(123)

        verify(nutritionRepository).get(any())
    }

    @Test
    fun getNutritionNames() = runTest {
        val result = getNutritionUseCase(123)

        Assertions.assertEquals(nutrition, result)
    }

    @Test
    fun returnNullOnFail() = runTest {
        whenever(nutritionRepository.get(any())).thenReturn(null)

        val result = getNutritionUseCase(4567)

        Assertions.assertEquals(null, result)
    }
}
