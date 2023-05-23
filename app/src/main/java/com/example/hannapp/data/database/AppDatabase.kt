package com.example.hannapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hannapp.data.database.dao.NutrimentLogDao
import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.database.dao.NutritionReferenceDao
import com.example.hannapp.data.model.entity.NutrimentLog
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.model.entity.NutritionReference

@Database(entities = [Nutrition::class, NutrimentLog::class, NutritionReference::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nutritionDao(): NutritionDao
    abstract fun nutrimentLogDao(): NutrimentLogDao
    abstract fun nutritionReferenceDao(): NutritionReferenceDao
}
