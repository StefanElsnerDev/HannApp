package com.example.hannapp.repository

import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.MilkReferenceRepository
import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.data.repository.NutritionLimitsRepository
import com.example.hannapp.ui.mood.Mood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutrimentLogValidationRepositoryShould {

    private lateinit var nutrimentLogValidationRepository: NutrimentLogValidationRepository
    private val nutritionLimitsRepository = mock<NutritionLimitsRepository>()
    private val nutrimentLogRepository = mock<NutrimentLogRepository>()
    private val milkReferenceRepository = mock<MilkReferenceRepository>()

    private val nutritionLimit = NutritionLimitReferenceModel(
        kcal = 10.0,
        protein = 10.0,
        carbohydrates = 10.0,
        fat = 10.0
    )

    private fun generateNutrimentLevelLog(
        limit: NutritionLimitReferenceModel,
        level: Float,
        logSize: Int
    ) = (1..logSize).map { index ->
        NutrimentLogModel(
            id = index.toLong(),
            nutrition = Nutrition(
                uid = index.toLong(),
                name = "",
                kcal = (level * limit.kcal).div(logSize),
                protein = (level * limit.protein).div(logSize),
                fat = (level * limit.fat).div(logSize),
                carbohydrates = (level * limit.carbohydrates).div(logSize)
            ),
            quantity = 1.0,
            createdAt = 1234567,
            modifiedAt = null
        )
    }

    private val milkReferenceUiModel = MilkReferenceUiModel(
        maxQuantity = 1060f,
        dayTimeQuantity = 340f,
        preNightQuantity = 80f,
        nightQuantity = 720f
    )

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(testDispatcher)

        whenever(nutritionLimitsRepository.getPreNightShare()).thenReturn(
            flowOf(nutritionLimit)
        )

        whenever(milkReferenceRepository.emitReference()).thenReturn(
            flowOf(milkReferenceUiModel)
        )

        nutrimentLogValidationRepository = NutrimentLogValidationRepository(
            nutritionLimitsRepository = nutritionLimitsRepository,
            nutrimentLogRepository = nutrimentLogRepository,
            milkReferenceRepository = milkReferenceRepository
        )
    }

    @Test
    fun emitGreenMoodOnEmptyNutriments() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            flowOf(
                generateNutrimentLevelLog(
                    limit = nutritionLimit,
                    level = 0.0f,
                    logSize = 5
                )
            )
        )

        val mood = nutrimentLogValidationRepository.validatePreNight().first()

        assertThat(mood).isEqualTo(Mood.GREEN)
    }

    @Test
    fun emitGreenMoodOnNutrimentLogBelow80PercentageOfDayLimit() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            flowOf(
                generateNutrimentLevelLog(
                    limit = nutritionLimit,
                    level = 0.79f,
                    logSize = 5
                )
            )
        )

        val mood = nutrimentLogValidationRepository.validatePreNight().first()

        assertThat(mood).isEqualTo(Mood.GREEN)
    }

    @Test
    fun emitYellowMoodOnNutrimentLogOn80PercentOfDayLimit() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            flowOf(
                generateNutrimentLevelLog(
                    limit = nutritionLimit,
                    level = 0.8f,
                    logSize = 5
                )
            )
        )

        val mood = nutrimentLogValidationRepository.validatePreNight().first()

        assertThat(mood).isEqualTo(Mood.YELLOW)
    }

    @Test
    fun emitYellowMoodOnNutrimentLogOn99PercentOfDayLimit() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            flowOf(
                generateNutrimentLevelLog(
                    limit = nutritionLimit,
                    level = 0.99f,
                    logSize = 5
                )
            )
        )

        val mood = nutrimentLogValidationRepository.validatePreNight().first()

        assertThat(mood).isEqualTo(Mood.YELLOW)
    }

    @Test
    fun emitRedMoodOnNutrimentLogOnExceedingDayLimit() = runTest {
        whenever(nutrimentLogRepository.getLogs()).thenReturn(
            flowOf(
                generateNutrimentLevelLog(
                    limit = nutritionLimit,
                    level = 1f,
                    logSize = 10
                )
            )
        )

        val mood = nutrimentLogValidationRepository.validatePreNight().first()

        assertThat(mood).isEqualTo(Mood.RED)
    }

    @Nested
    inner class CalculatePreNightOverFlow {

        @Test
        fun returnNoVolumeOnEmptyLog() = runTest {
            whenever(nutrimentLogRepository.getLogs()).thenReturn(
                flowOf(
                    emptyList()
                )
            )

            val volume = nutrimentLogValidationRepository.calculatePreNightOverflow().first()
            assertThat(volume).isEqualTo(0.0)
        }

        @Test
        fun returnCompleteVolumeOnExhaustedLog() = runTest {
            whenever(nutrimentLogRepository.getLogs()).thenReturn(
                flowOf(
                    listOf(
                        NutrimentLogModel(
                            id = 123,
                            nutrition = Nutrition(
                                protein = nutritionLimit.protein
                            ),
                            quantity = 123.0,
                            createdAt = 123456789,
                            modifiedAt = null
                        )
                    )
                )
            )

            val volume = nutrimentLogValidationRepository.calculatePreNightOverflow().first()

            assertThat(volume).isEqualTo(milkReferenceUiModel.preNightQuantity.toDouble())
        }
    }
}
