package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.data.repository.NutritionReferenceRepository
import com.example.hannapp.domain.SaveNutritionReferencesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class SaveNutritionReferencesUseCaseShould {

    private lateinit var saveNutritionReferencesUseCase: SaveNutritionReferencesUseCase
    private val nutritionReferenceRepository = mock<NutritionReferenceRepository>()
    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() {
        saveNutritionReferencesUseCase = SaveNutritionReferencesUseCase(
            nutritionReferenceRepository = nutritionReferenceRepository,
            dispatcher = dispatcher
        )
    }

    private val nutritionLimitReferenceUiModel = NutritionLimitReferenceUiModel(
        kcal = "123.45",
        protein = "45.6",
        carbohydrates = "78.9",
        fat = "9.0"
    )

    private val nutritionUiReferences = NutritionUiReferences(
        kcal = 123.45,
        protein = 45.6,
        carbohydrates = 78.9,
        fat = 9.0
    )

    @Test
    fun invokeRepository() = runTest {
        saveNutritionReferencesUseCase.invoke(nutritionLimitReferenceUiModel)

        verify(nutritionReferenceRepository).save(any())
    }

    @Test
    fun transformModelOnCallingRepo() = runTest {
        saveNutritionReferencesUseCase.invoke(nutritionLimitReferenceUiModel)

        verify(nutritionReferenceRepository).save(nutritionUiReferences)
    }

    @Test
    fun throwOnInvalidStringNumber() = runTest {
        val invalidModel = nutritionLimitReferenceUiModel.copy(kcal = "abc")

        assertThrows<RuntimeException> {
            saveNutritionReferencesUseCase.invoke(invalidModel)
        }
    }
}
