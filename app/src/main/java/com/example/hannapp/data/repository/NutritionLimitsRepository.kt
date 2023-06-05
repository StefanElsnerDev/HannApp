package com.example.hannapp.data.repository

import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.data.model.NutritionUiReferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class NutritionLimitsRepository @Inject constructor(
    private val milkReferenceRepository: MilkReferenceRepository,
    private val nutritionReferenceRepository: NutritionReferenceRepository
) {
    fun getDailyShare(): Flow<NutritionLimitReferenceModel> =
        milkReferenceRepository.emitReference()
            .combine(nutritionReferenceRepository.emitReference()) { milkReferenceUiModel: MilkReferenceUiModel, nutritionUiReference: NutritionUiReferences ->

                calculateLimit(
                    rate = milkReferenceUiModel.calculateDayTimeRate(),
                    nutritionUiReference = nutritionUiReference
                )
            }

    fun getPreNightShare(): Flow<NutritionLimitReferenceModel> =
        milkReferenceRepository.emitReference()
            .combine(nutritionReferenceRepository.emitReference()) { milkReferenceUiModel: MilkReferenceUiModel, nutritionUiReference: NutritionUiReferences ->

                calculateLimit(
                    rate = milkReferenceUiModel.calculatePreNightRate(),
                    nutritionUiReference = nutritionUiReference
                )
            }

    fun getNightShare(): Flow<NutritionLimitReferenceModel> =
        milkReferenceRepository.emitReference()
            .combine(nutritionReferenceRepository.emitReference()) { milkReferenceUiModel: MilkReferenceUiModel, nutritionUiReference: NutritionUiReferences ->

                calculateLimit(
                    rate = milkReferenceUiModel.calculateNightRate(),
                    nutritionUiReference = nutritionUiReference
                )
            }

    private fun calculateLimit(
        rate: Float,
        nutritionUiReference: NutritionUiReferences
    ) = NutritionLimitReferenceModel(
        kcal = (rate * nutritionUiReference.kcal).round(),
        protein = (rate * nutritionUiReference.protein).round(),
        carbohydrates = (rate * nutritionUiReference.carbohydrates).round(),
        fat = (rate * nutritionUiReference.fat).round()
    )

    private fun MilkReferenceUiModel.calculateDayTimeRate() = dayTimeQuantity / maxQuantity

    private fun MilkReferenceUiModel.calculatePreNightRate() = preNightQuantity / maxQuantity

    private fun MilkReferenceUiModel.calculateNightRate() = nightQuantity / maxQuantity

    private fun Double.round() = BigDecimal(this).setScale(1, RoundingMode.HALF_UP).toDouble()
}
