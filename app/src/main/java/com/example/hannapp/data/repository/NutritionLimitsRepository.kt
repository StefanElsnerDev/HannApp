package com.example.hannapp.data.repository

import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceModel
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.utils.round
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
}
