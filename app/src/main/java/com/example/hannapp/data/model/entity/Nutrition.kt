package com.example.hannapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hannapp.Constants.FOOD_NAME
import com.example.hannapp.Constants.NUTRITION_TABLE

@Entity(tableName = NUTRITION_TABLE)
data class Nutrition(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = FOOD_NAME) val name: String? = null,
    @ColumnInfo(name = "kcal per 100 g/ml") val kcal: Double? = null,
    @ColumnInfo(name = "protein per 100 g/ml") val protein: Double? = null,
    @ColumnInfo(name = "fat per 100 g/ml") val fat: Double? = null,
    @ColumnInfo(name = "carbohydrates per 100 g/ml") val carbohydrates: Double? = null,
    @ColumnInfo(name = "sugar of carbohydrates per 100 g/ml") val sugar: Double? = null,
    @ColumnInfo(name = "fiber per 100 g/ml") val fiber: Double? = null,
    @ColumnInfo(name = "alcohol per 100 g/ml") val alcohol: Double? = null,
    @ColumnInfo(name = "energy density per 100 g/ml") val energyDensity: Double? = null
)
