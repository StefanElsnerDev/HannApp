package com.example.hannapp.data.model.convert

import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.entity.Nutrition
import javax.inject.Inject

class NutritionConverter @Inject constructor() {

    fun model(nutritionModel: NutritionModel) = InnerNutritionModel(nutritionModel)
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
    }
}
