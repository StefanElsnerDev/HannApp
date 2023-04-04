package com.example.hannapp.data.distinct

import com.example.hannapp.Constants

enum class NutritionDataComponent(val text: String) {
    NAME(Constants.FOOD_NAME),
    KCAL(Constants.KCAL),
    PROTEIN(Constants.PROTEIN),
    FAT(Constants.FAT),
    CARBOHYDRATES(Constants.CARBOHYDRATES),
    SUGAR(Constants.SUGAR),
    FIBER(Constants.FIBER),
    ALCOHOL(Constants.ALCOHOL)
}
