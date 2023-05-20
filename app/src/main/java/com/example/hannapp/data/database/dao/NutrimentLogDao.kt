package com.example.hannapp.data.database.dao

import androidx.room.*
import com.example.hannapp.data.model.entity.NutrimentLog
import com.example.hannapp.data.model.entity.relation.Log
import kotlinx.coroutines.flow.Flow

@Dao
interface NutrimentLogDao {

    @Query("SELECT * FROM NutrimentLog WHERE id LIKE :logId")
    fun get(logId: Long): NutrimentLog

    @Insert
    suspend fun insert(nutrimentLog: NutrimentLog): Long

    @Update
    suspend fun update(nutrimentLog: NutrimentLog)

    @Transaction
    @Query("SELECT * FROM NutrimentLog")
    fun getLogs(): Flow<List<Log>>

    @Query("DELETE FROM NutrimentLog")
    suspend fun deleteAll(): Int
}
