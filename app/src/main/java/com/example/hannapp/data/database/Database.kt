package com.example.hannapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.model.entity.Nutrition

@Database(entities = [Nutrition::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nutritionDao(): NutritionDao
}
