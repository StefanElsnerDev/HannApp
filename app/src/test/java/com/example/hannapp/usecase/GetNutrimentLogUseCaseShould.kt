package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
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
        NutrimentUiLogModel(
            id = 1,
            nutrition = NutritionUiModel(name = "Apple"),
            quantity = 1.23,
            unit = "g / ml",
            timeStamp = 12345,
        ),
        NutrimentUiLogModel(
            id = 2,
            nutrition = NutritionUiModel(name = "Peach"),
            quantity = 9.87,
            unit = "g / ml",
            timeStamp = 987654321,
        )
    )
    private val logFlow = flowOf(nutrimentLogs)


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
        getNutrimentLogUseCase.observeNutrimentLog()

        verify(nutrimentLogRepository).getLogs()
    }

    @Test
    fun getLogs() = runTest {
        val result = getNutrimentLogUseCase.observeNutrimentLog().first()

        assertThat(result).isEqualTo(nutrimentLogs)
    }
}
