package com.example.hannapp.repository

import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.data.repository.MilkReferenceRepository
import com.example.hannapp.data.repository.NutritionLimitsRepository
import com.example.hannapp.data.repository.NutritionReferenceRepository
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
class NutritionLimitsRepositoryShould {

    private lateinit var nutritionLimitsRepository: NutritionLimitsRepository
    private val milkReferenceRepository = mock<MilkReferenceRepository>()
    private val nutritionReferenceRepository = mock<NutritionReferenceRepository>()

    private val milkReferenceUiModel = MilkReferenceUiModel(
        maxQuantity = 1060f,
        dayTimeQuantity = 720f,
        preNightQuantity = 80f,
        nightQuantity = 340f
    )

    private val nutritionUiReferences = NutritionUiReferences(
        kcal = 1755.0,
        protein = 29.5,
        carbohydrates = 314.3,
        fat = 43.0
    )

    private val dailyShare = NutritionLimitReferenceModel(
        kcal = 1192.1,
        protein = 20.0,
        carbohydrates = 213.5,
        fat = 29.2
    )

    private val preNightShare = NutritionLimitReferenceModel(
        kcal = 132.5,
        protein = 2.2,
        carbohydrates = 23.7,
        fat = 3.2
    )

    private val nightShare = NutritionLimitReferenceModel(
        kcal = 562.9,
        protein = 9.5,
        carbohydrates = 100.8,
        fat = 13.8
    )

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(milkReferenceRepository.emitReference()).thenReturn(
            flowOf(
                milkReferenceUiModel
            )
        )

        whenever(nutritionReferenceRepository.emitReference()).thenReturn(
            flowOf(
                nutritionUiReferences
            )
        )

        nutritionLimitsRepository = NutritionLimitsRepository(
            milkReferenceRepository = milkReferenceRepository,
            nutritionReferenceRepository = nutritionReferenceRepository
        )
    }

    @Test
    fun invokeDao() = runTest {
        nutritionLimitsRepository.getDailyShare()

        verify(milkReferenceRepository).emitReference()
        verify(nutritionReferenceRepository).emitReference()
    }

    @Test
    fun emitDailyShareModelWithRoundedLimits() = runTest {
        val result = nutritionLimitsRepository.getDailyShare().first()

        assertThat(result).isInstanceOf(NutritionLimitReferenceModel::class.java)
        assertThat(result).isEqualTo(dailyShare)

        result.assertThatRounded()
    }

    @Test
    fun emitPreNightShareModelWithRoundedLimits() = runTest {
        val result = nutritionLimitsRepository.getPreNightShare().first()

        assertThat(result).isInstanceOf(NutritionLimitReferenceModel::class.java)
        assertThat(result).isEqualTo(preNightShare)

        result.assertThatRounded()
    }

    @Test
    fun emitNightShareModelWithRoundedLimits() = runTest {
        val result = nutritionLimitsRepository.getNightShare().first()

        assertThat(result).isInstanceOf(NutritionLimitReferenceModel::class.java)
        assertThat(result).isEqualTo(nightShare)

        result.assertThatRounded()
    }

    private fun NutritionLimitReferenceModel.assertThatRounded() {
        assertThat(kcal.toString().substringAfter(delimiter = '.').length).isEqualTo(1)
        assertThat(protein.toString().substringAfter(delimiter = '.').length).isEqualTo(1)
        assertThat(carbohydrates.toString().substringAfter(delimiter = '.').length).isEqualTo(1)
        assertThat(fat.toString().substringAfter(delimiter = '.').length).isEqualTo(1)
    }
}
