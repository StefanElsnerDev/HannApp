package com.example.hannapp.usecase

import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import com.example.hannapp.domain.DeleteNutritionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteNutritionUseCaseShould {

    private lateinit var deleteNutritionUseCase: DeleteNutritionUseCase
    private val nutritionRepository: NutritionRepository = mock(NutritionRepository::class.java)

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutritionRepository.update(any())).thenReturn(true)
        deleteNutritionUseCase = DeleteNutritionUseCase(
            nutritionRepository
        )
    }

    @Test
    fun invokeDeleteOfRepository() = runTest {
        deleteNutritionUseCase.invoke(Nutrition())

        verify(nutritionRepository).delete(any())
    }
}
