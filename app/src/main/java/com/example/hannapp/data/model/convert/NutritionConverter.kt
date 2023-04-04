package com.example.hannapp.data.model.convert

import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.entity.Nutrition
import javax.inject.Inject

class NutritionConverter @Inject constructor() {

    fun model(nutritionModel: NutritionModel) = InnerNutritionModel(nutritionModel)

    fun uiModel(nutritionUiModel: NutritionUiModel) = InnerNutritionUiModel(nutritionUiModel)

    fun entity(nutrition: Nutrition) = InnerNutrition(nutrition)

    class InnerNutritionModel(private val nutritionModel: NutritionModel) {

        fun toEntity() = nutritionModel.let {
            Nutrition(
                uid = it.id ?: -1,
                name = it.name.ifBlank { null },
                kcal = it.kcal,
                protein = it.protein,
                fat = it.fat,
                carbohydrates = it.carbohydrates,
                sugar = it.sugar,
                fiber = it.fiber,
                alcohol = it.alcohol
            )
        }
    }

    class InnerNutritionUiModel(private val nutritionUiModel: NutritionUiModel) {

        fun toEntity() = nutritionUiModel.let {

            require(it.id != null){"Conversion failed due to missing entity ID"}

            Nutrition(
                uid = it.id,
                name = it.name.ifBlank { null },
                kcal = it.kcal.toDoubleOrNull(),
                protein = it.protein.toDoubleOrNull(),
                fat = it.fat.toDoubleOrNull(),
                carbohydrates = it.carbohydrates.toDoubleOrNull(),
                sugar = it.sugar.toDoubleOrNull(),
                fiber = it.fiber.toDoubleOrNull(),
                alcohol = it.alcohol.toDoubleOrNull()
            )
        }
    }
    
    class InnerNutrition(private val nutrition: Nutrition){
        
        fun toModel() = nutrition.let {
            NutritionModel(
                id = it.uid,
                name = it.name ?: "",
                kcal = it.kcal ?: 0.0,
                protein = it.protein ?: 0.0,
                fat = it.fat ?: 0.0,
                carbohydrates = it.carbohydrates ?: 0.0,
                sugar = it.sugar ?: 0.0,
                fiber = it.fiber ?: 0.0,
                alcohol = it.alcohol ?: 0.0
            )
        }

        fun toUiModel() = nutrition.let {
            NutritionUiModel(
                id = it.uid,
                name = it.name ?: "",
                kcal = it.kcal?.toString() ?: "",
                protein = it.protein?.toString() ?: "",
                fat = it.fat?.toString() ?: "",
                carbohydrates = it.carbohydrates?.toString() ?: "",
                sugar = it.sugar?.toString() ?: "",
                fiber = it.fiber?.toString() ?: "",
                alcohol = it.alcohol?.toString() ?: "",
            )
        }
    }
}
