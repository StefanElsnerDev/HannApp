package com.example.hannapp.data.repository

import com.example.hannapp.data.database.dao.NutrimentLogDao
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.convert.NutritionConverter
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
            lastModifiedAt = null
        )
    )

    fun getLogs(): Flow<List<NutrimentUiLogModel>> =
        nutrimentLogDao.getLogs().map { logList ->
            logList.map {
                NutrimentUiLogModel(
                    id = it.nutrimentLog.id,
                    nutrition = NutritionConverter.entity(it.nutrition).toUiModel(),
                    quantity = it.nutrimentLog.quantity,
                    unit = "g / ml",
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
