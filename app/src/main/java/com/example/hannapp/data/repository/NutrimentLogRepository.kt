package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutrimentLogDao
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.entity.NutrimentLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutrimentLogRepository @Inject constructor(
    private val nutrimentLogDao: NutrimentLogDao
) {
    suspend fun log(nutrimentId: Long, quantity: Double) = nutrimentLogDao.insert(
        NutrimentLog(
            id = 0,
            nutrimentId = nutrimentId,
            quantity = quantity,
            createdAt = System.currentTimeMillis(),
            lastModifiedAt = null,
        )
    )

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

    suspend fun update(logId: Long, nutrimentId: Long, quantity: Double) {

        val createdAt = nutrimentLogDao.get(logId).createdAt

        nutrimentLogDao.update(
            NutrimentLog(
                id = logId,
                nutrimentId = nutrimentId,
                quantity = quantity,
                createdAt = createdAt,
                lastModifiedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun clearLog(): Boolean {
        val deletedRows = nutrimentLogDao.deleteAll()
        return deletedRows > 0
    }
}
