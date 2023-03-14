package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutritionBMIDao
import com.example.hannapp.data.model.entity.NutritionBMI
import javax.inject.Inject

class NutritionRepository @Inject constructor(
    private val nutritionBMIDao: NutritionBMIDao,
) {
    suspend fun insert(nutritionBMI: NutritionBMI) = nutritionBMIDao.insert(nutritionBMI)

    fun get(nutritionName: String) = nutritionBMIDao.getByName(nutritionName)

    fun getNames() = nutritionBMIDao.getNames()

    suspend fun update(nutritionBMI: NutritionBMI) = nutritionBMIDao.update(nutritionBMI)

    suspend fun delete(nutritionBMI: NutritionBMI) = nutritionBMIDao.delete(nutritionBMI)

}

