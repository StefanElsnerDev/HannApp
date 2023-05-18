package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutrimentLogDao
import com.example.hannapp.data.model.NutrimentLogModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutrimentLogRepository @Inject constructor(
    private val nutrimentLogDao: NutrimentLogDao
) {
    suspend fun log(nutrimentLogModel: NutrimentLogModel) = nutrimentLogDao.log(nutrimentLogModel)

    fun getLogs(): Flow<List<NutrimentLogModel>> =
        nutrimentLogDao.getLogs().map { logList ->
            logList.map {
                NutrimentLogModel(
                    id = it.nutrimentLog.id,
                    nutrition = it.nutrition,
                    quantity = it.nutrimentLog.quantity,
                    createdAt = it.nutrimentLog.createdAt,
                    modifiedAt = it.nutrimentLog.lastModifiedAt
                )
            }
        }

    suspend fun clearLog(): Boolean {
        val deletedRows = nutrimentLogDao.deleteAll()
        return deletedRows > 0
    }
}
