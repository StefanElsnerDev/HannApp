package com.example.hannapp.data.database.dao

import androidx.room.*
import com.example.hannapp.Constants.NUTRITION_BMI_NAME
import com.example.hannapp.Constants.NUTRITION_BMI_TABLE
import com.example.hannapp.data.model.entity.NutritionBMI
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionBMIDao {
    @Insert
    suspend fun insert(nutritionBMI: NutritionBMI)

    @Query("SELECT * FROM $NUTRITION_BMI_TABLE")
    fun getAll(): Flow<List<NutritionBMI>>

    @Query("SELECT $NUTRITION_BMI_NAME FROM $NUTRITION_BMI_TABLE")
    fun getNames(): Flow<List<String>?>

    @Query("SELECT * FROM $NUTRITION_BMI_TABLE WHERE uid IN (:id)")
    fun getById(id: Int): Flow<NutritionBMI>

    @Query("SELECT * FROM $NUTRITION_BMI_TABLE WHERE $NUTRITION_BMI_NAME IN (:name)")
    fun getByName(name: String): Flow<NutritionBMI>

    @Update
    suspend fun update(nutritionBMI: NutritionBMI)

    @Delete
    suspend fun delete(nutritionBMI: NutritionBMI)
}
