package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.entity.Nutrition
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
    private val nutrimentLogModel = NutrimentLogModel(
        nutrition = Nutrition(name = "Banana", protein = 1.23),
        quantity = 4.56,
        createdAt = 123456789,
        modifiedAt = null
    )

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutrimentLogRepository.log(any())).thenReturn(1)

        insertNutrimentLogUseCase = InsertNutrimentLogUseCase(
            nutrimentLogRepository
        )
    }

    @Test
    fun invokeLogOfRepository() = runTest {
        insertNutrimentLogUseCase(nutrimentLogModel)

        verify(nutrimentLogRepository).log(any())
    }

    @Test
    fun returnBooleanForSuccess() = runTest {
        val result = insertNutrimentLogUseCase(nutrimentLogModel)

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun returnBooleanForFailure() = runTest {
        whenever(nutrimentLogRepository.log(any())).thenReturn(-1)

        val result = insertNutrimentLogUseCase(nutrimentLogModel)

        assertThat(result).isEqualTo(false)
    }
}
