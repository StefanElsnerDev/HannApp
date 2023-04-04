package com.example.hannapp.data.model

data class NutritionModel(
    val id: Long? = null,
    val name: String = "",
    val kcal: Double  = 0.0,
    val protein: Double  = 0.0,
    val fat: Double  = 0.0,
    val carbohydrates: Double  = 0.0,
    val sugar: Double  = 0.0,
    val fiber: Double  = 0.0,
    val alcohol: Double  = 0.0
)
