package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.domain.GetPreNightMaltoSubstitutionUseCase
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
class GetPreNightMaltoSubstitutionUseCaseShould {

    private lateinit var getPreNightMaltoSubstitutionUseCase: GetPreNightMaltoSubstitutionUseCase
    private val nutrimentLogValidationRepository = mock<NutrimentLogValidationRepository>()

    private val maltodextrinQuantity = 123.4

    @BeforeEach
    fun beforeEach() {
        whenever(nutrimentLogValidationRepository.calculatePreNightMaltodextrinSubstitution()).thenReturn(
            flowOf(maltodextrinQuantity)
        )

        getPreNightMaltoSubstitutionUseCase = GetPreNightMaltoSubstitutionUseCase(
            nutrimentLogValidationRepository = nutrimentLogValidationRepository
        )
    }

    @Test
    fun invokeRepository() {
        getPreNightMaltoSubstitutionUseCase()

        verify(nutrimentLogValidationRepository).calculatePreNightMaltodextrinSubstitution()
    }

    @Test
    fun getOverflow() = runTest {
        val quantity = nutrimentLogValidationRepository.calculatePreNightMaltodextrinSubstitution().first()

        assertThat(quantity).isEqualTo(maltodextrinQuantity)
    }
}
