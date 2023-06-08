package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.domain.GetNutrimentLogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNutrimentLogUseCaseShould {

    lateinit var getNutrimentLogUseCase: GetNutrimentLogUseCase
    private val nutrimentLogRepository = mock(NutrimentLogRepository::class.java)

    private val nutrimentLogs = listOf(
        NutrimentLogModel(
            id = 1,
            nutrition = Nutrition(
                uid = 1,
                name = "Apple"
            ),
            quantity = 1.23,
            createdAt = 12345,
            modifiedAt = 7878473
        ),
        NutrimentLogModel(
            id = 2,
            nutrition = Nutrition(
                uid = 2,
                name = "Peach"
            ),
            quantity = 9.87,
            createdAt = 987654321,
            modifiedAt = null
        )
    )

    private val logFlow = flowOf(nutrimentLogs)

    private val nutrimentUiLogs = listOf(
        NutrimentUiLogModel(
            id = 1,
            nutrition = NutritionUiModel(
                id = 1,
                name = "Apple"
            ),
            quantity = 1.23,
            unit = "g / ml",
            createdAt = 12345,
            modifiedAt = 7878473
        ),
        NutrimentUiLogModel(
            id = 2,
            nutrition = NutritionUiModel(
                id = 2,
                name = "Peach"
            ),
            quantity = 9.87,
            unit = "g / ml",
            createdAt = 987654321,
            modifiedAt = null
        )
    )

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            logFlow
        )

        getNutrimentLogUseCase = GetNutrimentLogUseCase(
            nutrimentLogRepository
        )
    }

    @Test
    fun invokeGetterOfRepository() = runTest {
        getNutrimentLogUseCase()

        verify(nutrimentLogRepository).getLogs()
    }

    @Test
    fun getLogs() = runTest {
        val result = getNutrimentLogUseCase().first()

        assertThat(result).isEqualTo(nutrimentUiLogs)
    }
}
