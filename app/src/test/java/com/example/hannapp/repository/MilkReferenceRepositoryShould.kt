package com.example.hannapp.repository

import com.example.hannapp.data.model.MilkReferenceModel
import com.example.hannapp.data.repository.MilkReferenceRepository
import com.example.hannapp.data.source.MilkReferenceDataSource
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

@OptIn(ExperimentalCoroutinesApi::class)
class MilkReferenceRepositoryShould {

    private lateinit var milkReferenceRepository: MilkReferenceRepository
    private val milkReferenceDataSource = mock<MilkReferenceDataSource>()

    private val milkReferenceModel = MilkReferenceModel(
        maxQuantity = 123.4f,
        dayTimeQuantity = 24.34f,
        preNightQuantity = 22.1f,
        nightQuantity = 223.11f
    )

    private val propertyCount = 4

    @BeforeEach
    fun beforeEach() {
        milkReferenceRepository = MilkReferenceRepository(
            milkReferenceDataSource = milkReferenceDataSource
        )
    }

    @Nested
    inner class Save {

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(milkReferenceDataSource.save(any())).thenReturn(
                propertyCount
            )
        }

        @Test
        fun invokeDataSource() = runTest {
            milkReferenceRepository.save(milkReferenceModel)

            verify(milkReferenceDataSource).save(milkReferenceModel)
        }

        @Test
        fun returnCountOfChangedProperties() = runTest {
            val result = milkReferenceRepository.save(milkReferenceModel)

            assertThat(result).isEqualTo(propertyCount)
        }

        @Test
        fun throwOnMissingProperties() = runTest {
            assertThrows<IllegalArgumentException> {
                milkReferenceRepository.save(milkReferenceModel.copy(maxQuantity = null))
            }
        }
    }

    @Nested
    inner class EmitReferences {

        private val referencesFlow = flowOf(milkReferenceModel)

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(milkReferenceDataSource.emitReferences()).thenReturn(
                referencesFlow
            )
        }

        @Test
        fun invokeDataSource() = runTest {
            milkReferenceRepository.emitReference()

            verify(milkReferenceDataSource).emitReferences()
        }

        @Test
        fun emitNutritionReferencesFromDataSource() = runTest {
            val result = milkReferenceRepository.emitReference()

            assertThat(result).isEqualTo(referencesFlow)
            assertThat(result.first()).isEqualTo(milkReferenceModel)
        }
    }
}
