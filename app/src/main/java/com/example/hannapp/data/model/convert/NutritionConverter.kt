package com.example.hannapp.data.model.convert

import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.entity.Nutrition

class NutritionConverter {

    fun model(nutritionModel: NutritionModel) = InnerNutritionModel(nutritionModel)

    class InnerNutritionModel(private val nutritionModel: NutritionModel) {

        fun toEntity() = nutritionModel.let {
            Nutrition(
                uid = it.id ?: -1,
                name = it.name.ifBlank { null },
                kcal = it.kcal.ifBlank { null },
                protein = it.protein.ifBlank { null },
                fad = it.fad.ifBlank { null },
                carbohydrates = it.carbohydrates.ifBlank { null },
                sugar = it.sugar.ifBlank { null },
                fiber = it.fiber.ifBlank { null },
                alcohol = it.alcohol.ifBlank { null },
                energyDensity = it.energy.ifBlank { null }
            )
        }
    }
}
