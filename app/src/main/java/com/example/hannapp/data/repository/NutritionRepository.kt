package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutritionBMIDao
import com.example.hannapp.data.model.entity.NutritionBMI
import com.example.hannapp.data.modul.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NutritionRepository @Inject constructor(
    private val nutritionBMIDao: NutritionBMIDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun insert(nutritionBMI: NutritionBMI) =
        withContext(dispatcher) {
            nutritionBMIDao.insert(nutritionBMI)
        }

    suspend fun get(nutritionName: String) =
        withContext(dispatcher) {
            nutritionBMIDao.getByName(nutritionName)
        }

    suspend fun getNames() =
        withContext(dispatcher) {
            nutritionBMIDao.getNames()
        }

    suspend fun update(nutritionBMI: NutritionBMI) =
        withContext(dispatcher) {
            nutritionBMIDao.update(nutritionBMI)
        }

    suspend fun delete(nutritionBMI: NutritionBMI) =
        withContext(dispatcher) {
            nutritionBMIDao.delete(nutritionBMI)
        }
}

