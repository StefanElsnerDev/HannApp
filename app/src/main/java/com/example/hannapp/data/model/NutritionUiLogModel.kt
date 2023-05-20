package com.example.hannapp.data.model

data class NutrimentUiLogModel(
    val id: Long,
    val nutrition: NutritionUiModel,
    val quantity: Double,
    val unit: String,
    val createdAt: Long,
    val modifiedAt: Long?,
)
