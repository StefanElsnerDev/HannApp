package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.domain.ValidateNutrimentLogUseCase
import com.example.hannapp.ui.mood.Mood
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ValidateNutrimentLogUseCaseShould {

    private lateinit var validateNutrimentLogUseCase: ValidateNutrimentLogUseCase
    private val nutrimentLogValidationRepository = mock<NutrimentLogValidationRepository>()

    @BeforeEach
    fun beforeEach() {
        whenever(nutrimentLogValidationRepository.validatePreNight()).thenReturn(
            flowOf(Mood.GREEN)
        )

        validateNutrimentLogUseCase = ValidateNutrimentLogUseCase(
            nutrimentLogValidationRepository = nutrimentLogValidationRepository
        )
    }

    @Test
    fun invokeValidationOfNutritionLimitRepository() = runTest {
        validateNutrimentLogUseCase.invoke()

        verify(nutrimentLogValidationRepository).validatePreNight()
    }

    @Test
    fun emitMoodOnLogBelowLimit() = runTest {
        val mood = validateNutrimentLogUseCase.invoke().first()

        assertThat(mood).isInstanceOf(Mood::class.java)
        assertThat(mood).isEqualTo(Mood.GREEN)
    }
}
