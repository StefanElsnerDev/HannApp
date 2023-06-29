package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.domain.GetPreNightMilkDiscardUseCase
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
class GetPreNightMilkDiscardUseCaseShould {

    private lateinit var getPreNightMilkDiscardUseCase: GetPreNightMilkDiscardUseCase
    private val nutrimentLogValidationRepository = mock<NutrimentLogValidationRepository>()

    private val overflowQuantity = 123.4

    @BeforeEach
    fun beforeEach() {
        whenever(nutrimentLogValidationRepository.calculatePreNightMilkDiscard()).thenReturn(
            flowOf(overflowQuantity)
        )

        getPreNightMilkDiscardUseCase = GetPreNightMilkDiscardUseCase(
            nutrimentLogValidationRepository = nutrimentLogValidationRepository
        )
    }

    @Test
    fun invokeRepository() {
        getPreNightMilkDiscardUseCase()

        verify(nutrimentLogValidationRepository).calculatePreNightMilkDiscard()
    }

    @Test
    fun getOverflow() = runTest {
        val quantity = nutrimentLogValidationRepository.calculatePreNightMilkDiscard().first()

        assertThat(quantity).isEqualTo(overflowQuantity)
    }
}
