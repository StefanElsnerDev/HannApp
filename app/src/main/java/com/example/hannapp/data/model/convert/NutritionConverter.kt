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
                kcal = it.kcal.ifBlank { null },
                protein = it.protein.ifBlank { null },
                fat = it.fat.ifBlank { null },
                carbohydrates = it.carbohydrates.ifBlank { null },
                sugar = it.sugar.ifBlank { null },
                fiber = it.fiber.ifBlank { null },
                alcohol = it.alcohol.ifBlank { null },
                energyDensity = it.energy.ifBlank { null }
            )
        }
    }
    
    class InnerNutrition(private val nutrition: Nutrition){
        
        fun toModel() = nutrition.let {
            NutritionModel(
                id = it.uid,
                name = it.name ?: "",
                kcal = it.kcal ?: "",
                protein = it.protein ?: "",
                fat = it.fat ?: "",
                carbohydrates = it.carbohydrates ?: "",
                sugar = it.sugar ?: "",
                fiber = it.fiber ?: "",
                alcohol = it.alcohol ?: "",
                energy = it.energyDensity ?: ""
            )
        }
    }
}
