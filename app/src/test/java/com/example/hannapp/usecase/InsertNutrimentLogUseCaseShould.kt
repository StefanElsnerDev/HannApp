package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class InsertNutrimentLogUseCaseShould {

    private lateinit var insertNutrimentLogUseCase: InsertNutrimentLogUseCase
    private val nutrimentLogRepository = mock(NutrimentLogRepository::class.java)

    private val nutrimentId = 17352L
    private val quantity = 4.56

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutrimentLogRepository.log(any(), any())).thenReturn(1)

        insertNutrimentLogUseCase = InsertNutrimentLogUseCase(
            nutrimentLogRepository
        )
    }

    @Test
    fun invokeLogOfRepository() = runTest {
        insertNutrimentLogUseCase(
            nutrimentId = nutrimentId,
            quantity = quantity
        )

        verify(nutrimentLogRepository).log(any(), any())
    }

    @Test
    fun returnBooleanForSuccess() = runTest {
        val result = insertNutrimentLogUseCase(
            nutrimentId = nutrimentId,
            quantity = quantity
        )

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun returnBooleanForFailure() = runTest {
        whenever(nutrimentLogRepository.log(any(), any())).thenReturn(-1)

        val result = insertNutrimentLogUseCase(
            nutrimentId = nutrimentId,
            quantity = quantity
        )

        assertThat(result).isEqualTo(false)
    }
}
