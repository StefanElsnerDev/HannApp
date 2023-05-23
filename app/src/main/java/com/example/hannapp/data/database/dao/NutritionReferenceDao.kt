package com.example.hannapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hannapp.Constants.NUTRITION_REFERENCE_TABLE
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.model.entity.NutritionReference

@Dao
interface NutritionReferenceDao {
    @Insert
    suspend fun insert(nutritionReference: NutritionReference): Long

    @Query("SELECT * FROM $NUTRITION_REFERENCE_TABLE")
    suspend fun get(): NutritionReference

    @Update
    suspend fun update(nutrition: Nutrition): Int

    @Delete
    suspend fun delete(nutrition: Nutrition)
}
