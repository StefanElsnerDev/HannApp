package com.example.hannapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hannapp.Constants.NUTRITION_BMI_NAME
import com.example.hannapp.Constants.NUTRITION_BMI_TABLE
import com.example.hannapp.data.model.entity.NutritionBMI

@Dao
interface NutritionBMIDao {
    @Query("SELECT * FROM $NUTRITION_BMI_TABLE")
    fun getAll(): List<NutritionBMI>

    @Query("SELECT * FROM $NUTRITION_BMI_TABLE WHERE uid IN (:id)")
    fun getById(id: Int): NutritionBMI

    @Query("SELECT * FROM $NUTRITION_BMI_TABLE WHERE $NUTRITION_BMI_NAME IN (:name)")
    fun getByName(name: String): NutritionBMI

    @Insert
    fun insert(nutritionBMI: NutritionBMI)

    @Delete
    fun delete(nutritionBMI: NutritionBMI)
}
