package com.example.hannapp.data.database.dao

import androidx.room.*
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.entity.NutrimentLog
import com.example.hannapp.data.model.entity.relation.Log
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NutrimentLogDao {
    @Insert
    suspend fun log(nutrimentLogModel: NutrimentLogModel): Long =
        insert(
            NutrimentLog(
                nutrimentId = nutrimentLogModel.nutrition.uid,
                quantity = nutrimentLogModel.quantity,
                createdAt = nutrimentLogModel.createdAt,
                lastModifiedAt = nutrimentLogModel.modifiedAt
            )
        )

    @Insert
    abstract suspend fun insert(nutrimentLog: NutrimentLog): Long

    @Transaction
    @Query("SELECT * FROM NutrimentLog")
    abstract fun getLogs(): Flow<List<Log>>

    @Query("DELETE FROM NutrimentLog")
    abstract suspend fun deleteAll(): Int
}
