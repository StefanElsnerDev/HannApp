package com.example.hannapp.ui

import com.example.hannapp.data.distinct.NutritionDataComponent
import com.example.hannapp.data.model.NutritionModel

fun mapComponentToModelProperty(
    type: NutritionDataComponent,
    model: NutritionModel
) = when (type) {
    NutritionDataComponent.NAME -> model.name
    NutritionDataComponent.KCAL -> model.kcal
    NutritionDataComponent.PROTEIN -> model.protein
    NutritionDataComponent.FAD -> model.fad
    NutritionDataComponent.CARBOHYDRATES -> model.carbohydrates
    NutritionDataComponent.SUGAR -> model.sugar
    NutritionDataComponent.FIBER -> model.fiber
    NutritionDataComponent.ALCOHOL -> model.alcohol
    NutritionDataComponent.ENERGY -> model.energy
}
