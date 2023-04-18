package com.example.hannapp.data.model

data class NutrimentUiLogModel(
    val nutrition: NutritionUiModel,
    val quantity: Double,
    val unit: String,
    val timeStamp: Long
)
