package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.model.entity.Nutrition
import javax.inject.Inject

class NutritionRepository @Inject constructor(
    private val nutritionDao: NutritionDao,
) {
    suspend fun insert(nutrition: Nutrition) = nutritionDao.insert(nutrition)

    suspend fun get(id: Int) = nutritionDao.getById(id)

    fun getFood() = nutritionDao.getFood()

    suspend fun update(nutrition: Nutrition) = nutritionDao.update(nutrition) == 1

    suspend fun delete(nutrition: Nutrition) = nutritionDao.delete(nutrition)

}

