package com.example.hannapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hannapp.Constants.NUTRITION_BMI_NAME
import com.example.hannapp.Constants.NUTRITION_BMI_TABLE

@Entity(tableName = NUTRITION_BMI_TABLE)
data class NutritionBMI(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = NUTRITION_BMI_NAME) val name: String? = null,
    @ColumnInfo(name = "kcal per 100 g/ml") val kcal: String? = null,
    @ColumnInfo(name = "protein per 100 g/ml") val protein: String? = null,
    @ColumnInfo(name = "fad per 100 g/ml") val fad: String? = null,
    @ColumnInfo(name = "carbohydrates per 100 g/ml") val carbohydrates: String? = null,
    @ColumnInfo(name = "sugar of carbohydrates per 100 g/ml") val sugar: String? = null,
    @ColumnInfo(name = "fiber per 100 g/ml") val fiber: String? = null,
    @ColumnInfo(name = "alcohol per 100 g/ml") val alcohol: String? = null,
    @ColumnInfo(name = "energy density per 100 g/ml") val energyDensity: String? = null
)
