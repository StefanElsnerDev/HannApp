package com.example.hannapp.data.repository

import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.ui.mood.Mood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class NutrimentLogValidationRepository @Inject constructor(
    private val nutritionLimitsRepository: NutritionLimitsRepository,
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    fun validatePreNight(): Flow<Mood> = nutrimentLogRepository.getLogs()
        .combine(nutritionLimitsRepository.getPreNightShare()) { logModels, limit ->

            val levels = calcNutritionLevels(logModels, limit)

            when {
                levels.any { it >= 1 } -> Mood.RED
                levels.any { it >= 0.8f } -> Mood.YELLOW
                else -> Mood.GREEN
            }
        }

    private fun calcLevel(quantities: List<Double>, limit: Double): Float =
        quantities.sumOf { it }.div(limit).toFloat()

    private fun calcNutritionLevels(
        logModels: List<NutrimentLogModel>,
        limit: NutritionLimitReferenceModel
    ): List<Float> {
        return listOf(
            calcLevel(
                quantities = logModels.map { it.nutrition.protein ?: 0.0 },
                limit = limit.protein
            ),
            calcLevel(
                quantities = logModels.map { it.nutrition.carbohydrates ?: 0.0 },
                limit = limit.carbohydrates
            ),
            calcLevel(
                quantities = logModels.map { it.nutrition.fat ?: 0.0 },
                limit = limit.fat
            )
        )
    }
}
