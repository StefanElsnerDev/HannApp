package com.example.hannapp.ui.viewmodel

interface Reference

enum class NutritionReference : Reference {
    KCAL,
    PROTEIN,
    CARBOHYDRATES,
    FAT
}

enum class MilkReference : Reference {
    TOTAL,
    PRE_NIGHT,
    NIGHT
}
