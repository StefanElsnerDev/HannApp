package com.example.hannapp.usecase

import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import com.example.hannapp.domain.UpdateNutritionUseCase
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
class UpdateNutritionUseCaseShould {

    private lateinit var updateNutritionUseCase: UpdateNutritionUseCase
    private val nutritionRepository: NutritionRepository = mock(NutritionRepository::class.java)

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutritionRepository.update(any())).thenReturn(true)
        updateNutritionUseCase = UpdateNutritionUseCase(
            nutritionRepository
        )
    }

    @Test
    fun invokeInsertOfRepository() = runTest {
        updateNutritionUseCase.invoke(Nutrition())

        verify(nutritionRepository).update(any())
    }

    @Test
    fun returnSuccessfulUpdate() = runTest {
        whenever(nutritionRepository.update(any())).thenReturn(true)

        val result = updateNutritionUseCase.invoke(Nutrition())

        Assertions.assertEquals(true, result)
    }

    @Test
    fun returnFailingUpdate() = runTest {
        whenever(nutritionRepository.update(any())).thenReturn(false)

        val result = updateNutritionUseCase.invoke(Nutrition())

        Assertions.assertEquals(false, result)
    }
}
