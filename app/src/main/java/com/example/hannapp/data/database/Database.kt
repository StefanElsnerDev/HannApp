package com.example.hannapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hannapp.data.database.dao.NutritionBMIDao
import com.example.hannapp.data.model.entity.NutritionBMI

@Database(entities = [NutritionBMI::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nutritionBMIDao(): NutritionBMIDao
}
