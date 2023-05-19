package com.example.hannapp.data.database.dao

import androidx.room.*
import com.example.hannapp.data.model.entity.NutrimentLog
import com.example.hannapp.data.model.entity.relation.Log
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NutrimentLogDao {

    @Query("SELECT * FROM NutrimentLog WHERE id LIKE :logId")
    abstract fun get(logId: Long): NutrimentLog

    @Insert
    abstract suspend fun insert(nutrimentLog: NutrimentLog): Long

    @Update
    abstract suspend fun update(nutrimentLog: NutrimentLog)

    @Transaction
    @Query("SELECT * FROM NutrimentLog")
    abstract fun getLogs(): Flow<List<Log>>

    @Query("DELETE FROM NutrimentLog")
    abstract suspend fun deleteAll(): Int
}
