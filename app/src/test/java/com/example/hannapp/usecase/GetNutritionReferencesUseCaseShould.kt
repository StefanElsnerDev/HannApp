package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.data.repository.NutritionReferenceRepository
import com.example.hannapp.domain.GetNutritionReferencesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNutritionReferencesUseCaseShould {

    private lateinit var getNutritionReferencesUseCase: GetNutritionReferencesUseCase
    private val nutritionReferenceRepository = mock<NutritionReferenceRepository>()

    private val nutritionUiReferences = NutritionUiReferences(
        kcal = 1.2,
        protein = 2.3,
        carbohydrates = 3.4,
        fat = 4.5
    )

    private val nutritionLimitReferenceUiModel = NutritionLimitReferenceUiModel(
        kcal = "1.2",
        protein = "2.3",
        carbohydrates = "3.4",
        fat = "4.5"
    )

    @BeforeEach
    fun beforeEach() {
        whenever(nutritionReferenceRepository.emitReference()).thenReturn(
            flowOf(
                nutritionUiReferences
            )
        )

        getNutritionReferencesUseCase = GetNutritionReferencesUseCase(
            nutritionReferenceRepository = nutritionReferenceRepository,
            dispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun invokeRepository() {
        getNutritionReferencesUseCase.invoke()

        verify(nutritionReferenceRepository).emitReference()
    }

    @Test
    fun emitNutritionReferences() = runTest {
        getNutritionReferencesUseCase.invoke()

        val result = getNutritionReferencesUseCase.invoke().first()

        assertThat(result).isEqualTo(nutritionLimitReferenceUiModel)
    }
}
