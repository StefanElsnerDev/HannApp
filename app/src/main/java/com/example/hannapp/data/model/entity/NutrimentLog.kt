package com.example.hannapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NutrimentLog(
    @PrimaryKey(autoGenerate = true)  val id: Long = 0,
    val nutrimentId: Long,
    val quantity: Double,
    val createdAt: Long,
    val lastModifiedAt: Long? = null
)
