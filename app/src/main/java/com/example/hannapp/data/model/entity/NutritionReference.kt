package com.example.hannapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hannapp.Constants

@Entity(tableName = Constants.NUTRITION_REFERENCE_TABLE)
data class NutritionReference(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "total_milk_quantity") val quantity: Double,
    @ColumnInfo(name = "day_share_quantity") val dayQuantity: Double,
    @ColumnInfo(name = "pre_night_share_quantity") val preNightQuantity: Double,
    @ColumnInfo(name = "night_share_quantity") val nightQuantity: Double,
    val kcal: Double,
    val protein: Double,
    val carbohydrates: Double,
    val fat: Double
)
