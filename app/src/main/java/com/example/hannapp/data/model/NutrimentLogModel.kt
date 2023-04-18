package com.example.hannapp.data.model

import com.example.hannapp.data.model.entity.Nutrition

data class NutrimentLogModel(
    val nutrition: Nutrition,
    val quantity: Double,
    val createdAt: Long,
    val modifiedAt: Long?
)
