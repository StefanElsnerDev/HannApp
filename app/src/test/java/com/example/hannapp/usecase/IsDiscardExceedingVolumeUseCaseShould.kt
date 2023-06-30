package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.domain.IsDiscardExceedingVolumeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class IsDiscardExceedingVolumeUseCaseShould {

    private lateinit var isDiscardExceedingVolumeUseCase: IsDiscardExceedingVolumeUseCase
    private val nutrimentLogValidationRepository = mock<NutrimentLogValidationRepository>()

    @BeforeEach
    fun beforeEeach() {
        isDiscardExceedingVolumeUseCase = IsDiscardExceedingVolumeUseCase(
            nutrimentLogValidationRepository = nutrimentLogValidationRepository
        )
    }

    @Test
    fun emitExceeding() = runTest {
        whenever(nutrimentLogValidationRepository.isPreNightDiscardExceedingVolume()).thenReturn(
            flowOf(true)
        )

        assertThat(isDiscardExceedingVolumeUseCase().first()).isTrue
    }

    @Test
    fun emitNoExceeding() = runTest {
        whenever(nutrimentLogValidationRepository.isPreNightDiscardExceedingVolume()).thenReturn(
            flowOf(false)
        )

        assertThat(isDiscardExceedingVolumeUseCase().first()).isFalse
    }
}
