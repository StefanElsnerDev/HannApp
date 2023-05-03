package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteNutrimentLogUseCaseShould {

    lateinit var deleteNutrimentLogUseCase: DeleteNutrimentLogUseCase
    private val nutrimentLogRepository = mock(NutrimentLogRepository::class.java)

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutrimentLogRepository.clearLog()).thenReturn(true)

        deleteNutrimentLogUseCase = DeleteNutrimentLogUseCase(
            nutrimentLogRepository
        )
    }

    @Test
    fun invokeDeletionMethodOfRepository() = runTest {
        deleteNutrimentLogUseCase.clear()

        verify(nutrimentLogRepository).clearLog()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun returnDeletionResultAsBoolean(returnValue: Boolean) = runTest {
        whenever(nutrimentLogRepository.clearLog()).thenReturn(returnValue)

        val result = deleteNutrimentLogUseCase.clear()

        assertThat(result).isEqualTo(returnValue)
    }
}
