package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutritionRepository
import com.example.hannapp.domain.GetNutritionBMIsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNutritionBMIsUseCaseShould {

    lateinit var getNutritionBMIsUseCase: GetNutritionBMIsUseCase
    private val nutritionRepository: NutritionRepository = mock(NutritionRepository::class.java)
    private val nutritionNames = listOf("Apple", "Banana", "Grapefruit")


    @BeforeEach
    fun beforeEach() {
        whenever(nutritionRepository.getNames()).thenReturn(
            flowOf(nutritionNames)
        )

        getNutritionBMIsUseCase = GetNutritionBMIsUseCase(
            nutritionRepository
        )
    }

    @Test
    fun invokeGetterOfRepository() {
        getNutritionBMIsUseCase()

        verify(nutritionRepository).getNames()
    }

    @Test
    fun getNutritionNames() = runTest {
        val result = getNutritionBMIsUseCase().first()

        Assertions.assertEquals(nutritionNames, result)
    }
}
