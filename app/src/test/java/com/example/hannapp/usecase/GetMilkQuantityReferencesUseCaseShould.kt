package com.example.hannapp.usecase

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.repository.MilkReferenceRepository
import com.example.hannapp.domain.GetMilkQuantityReferencesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetMilkQuantityReferencesUseCaseShould {

    private lateinit var getMilkQuantityReferencesUseCase: GetMilkQuantityReferencesUseCase
    private val milkReferenceRepository = mock<MilkReferenceRepository>()

    private val milkLimitReferenceUiModel = MilkLimitReferenceUiModel(
        total = "100.0",
        day = "50.0",
        preNight = "50.5",
        night = "100.0"
    )

    private val milkReferenceUiModel = MilkReferenceUiModel(
        maxQuantity = 100.0f,
        dayTimeQuantity = 50.0f,
        preNightQuantity = 50.5f,
        nightQuantity = 100.0f
    )

    @BeforeEach
    fun beforeEach() {
        whenever(milkReferenceRepository.emitReference()).thenReturn(
            flowOf(
                milkReferenceUiModel
            )
        )

        getMilkQuantityReferencesUseCase = GetMilkQuantityReferencesUseCase(
            milkReferenceRepository = milkReferenceRepository,
            dispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun invokeRepository() {
        getMilkQuantityReferencesUseCase.invoke()

        verify(milkReferenceRepository).emitReference()
    }

    @Test
    fun emitNutritionReferences() = runTest {
        getMilkQuantityReferencesUseCase.invoke()

        val result = getMilkQuantityReferencesUseCase.invoke().first()

        assertThat(result).isEqualTo(milkLimitReferenceUiModel)
    }
}
