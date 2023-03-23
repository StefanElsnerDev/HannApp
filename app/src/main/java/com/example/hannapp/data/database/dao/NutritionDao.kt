package com.example.hannapp.data.database.dao

import androidx.room.*
import com.example.hannapp.Constants.FOOD_NAME
import com.example.hannapp.Constants.NUTRITION_TABLE
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.model.entity.Nutrition
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDao {
    @Insert
    suspend fun insert(nutrition: Nutrition): Long

    @Query("SELECT * FROM $NUTRITION_TABLE")
    fun getAll(): Flow<List<Nutrition>>

    @Query("SELECT uid, $FOOD_NAME FROM $NUTRITION_TABLE")
    fun getFood(): Flow<List<Food>?>

    @Query("SELECT * FROM $NUTRITION_TABLE WHERE uid IN (:id)")
    fun getById(id: Int): Flow<Nutrition>

    @Query("SELECT * FROM $NUTRITION_TABLE WHERE $FOOD_NAME IN (:name)")
    fun getByName(name: String): Flow<Nutrition>

    @Update
    suspend fun update(nutrition: Nutrition)

    @Delete
    suspend fun delete(nutrition: Nutrition)
}
