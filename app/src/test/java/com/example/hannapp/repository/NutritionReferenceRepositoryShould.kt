package com.example.hannapp.repository

import com.example.hannapp.data.model.NutritionReferences
import com.example.hannapp.data.repository.NutritionReferenceRepository
import com.example.hannapp.data.source.NutritionReferenceDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.IllegalArgumentException

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionReferenceRepositoryShould {

    private lateinit var nutritionReferenceRepository: NutritionReferenceRepository
    private val nutritionReferenceDataSource = mock<NutritionReferenceDataSource>()

    private val nutritionReferences = NutritionReferences(
        kcal = 123.4,
        protein = 892.3,
        carbohydrates = 293.1,
        fat = 221.2
    )

    private val propertyCount = 4

    @BeforeEach
    fun beforeEach() {
        nutritionReferenceRepository = NutritionReferenceRepository(
            nutritionReferenceDataSource = nutritionReferenceDataSource
        )
    }

    @Nested
    inner class Save {

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(nutritionReferenceDataSource.save(any())).thenReturn(
                propertyCount
            )
        }

        @Test
        fun invokeDataSource() = runTest {
            nutritionReferenceRepository.save(nutritionReferences)

            verify(nutritionReferenceDataSource).save(nutritionReferences)
        }

        @Test
        fun returnCountOfChangedProperties() = runTest {
            val result = nutritionReferenceRepository.save(nutritionReferences)

            assertThat(result).isEqualTo(propertyCount)
        }

        @Test
        fun throwOnMissingProperties() = runTest {
            assertThrows<IllegalArgumentException> {
                nutritionReferenceRepository.save(nutritionReferences.copy(kcal = null))
            }
        }
    }

    @Nested
    inner class EmitReferences {

        private val referencesFlow = flowOf(nutritionReferences)

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(nutritionReferenceDataSource.emitReferences()).thenReturn(
                referencesFlow
            )
        }

        @Test
        fun invokeDataSource() = runTest {
            nutritionReferenceRepository.emitReference()

            verify(nutritionReferenceDataSource).emitReferences()
        }

        @Test
        fun emitNutritionReferencesFromDataSource() = runTest {
            val result = nutritionReferenceRepository.emitReference()

            assertThat(result).isEqualTo(referencesFlow)
            assertThat(result.first()).isEqualTo(nutritionReferences)
        }
    }
}
