package com.example.hannapp.data.repository

import com.example.hannapp.R
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.ui.mood.Mood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class NutrimentLogValidationRepository @Inject constructor(
    private val nutritionLimitsRepository: NutritionLimitsRepository,
    private val nutrimentLogRepository: NutrimentLogRepository,
    private val milkReferenceRepository: MilkReferenceRepository,
    private val substitutionRepository: SubstitutionRepository
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
                quantities = logModels.map { (it.nutrition.protein?.times(it.quantity)) ?: 0.0 },
                limit = limit.protein
            ),
            calcLevel(
                quantities = logModels.map { (it.nutrition.carbohydrates?.times(it.quantity)) ?: 0.0 },
                limit = limit.carbohydrates
            ),
            calcLevel(
                quantities = logModels.map { (it.nutrition.fat?.times(it.quantity)) ?: 0.0 },
                limit = limit.fat
            )
        )
    }

    fun calculatePreNightMilkDiscard() = nutrimentLogRepository.getLogs()
        .combine(nutritionLimitsRepository.getPreNightShare()) { logModels, limit ->
            val proteinSum = logModels.sumOf { (it.nutrition.protein?.times(it.quantity)) ?: 0.0 }
            val proteinOfShare = limit.protein
            proteinSum.div(proteinOfShare)
        }.combine(milkReferenceRepository.emitReference()) { rate, milkQuantities ->
            rate.times(milkQuantities.preNightQuantity)
        }

    fun isPreNightDiscardExceedingVolume() = calculatePreNightMilkDiscard()
        .combine(milkReferenceRepository.emitReference()) { discard, limit ->
            discard > limit.preNightQuantity
        }

    private fun calculatePreNightEnergySubstitution() =
        calculatePreNightMilkDiscard().combine(milkReferenceRepository.emitReference()) { volume, milkReferences ->
            volume.div(milkReferences.preNightQuantity)
        }.combine(nutritionLimitsRepository.getPreNightShare()) { rate, limit ->
            rate.times(limit.kcal)
        }

    fun calculatePreNightMaltodextrinSubstitution() =
        calculatePreNightEnergySubstitution().combine(
            substitutionRepository.getMaltoDextrin()
        ) { missingKcal, malto ->
            require(malto.kcal != null) { R.string.missing_nutriments_malto }
            missingKcal.times(100).div(malto.kcal)
        }
}
