package com.example.hannapp.data.model.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.hannapp.data.model.entity.NutrimentLog
import com.example.hannapp.data.model.entity.Nutrition

data class Log(
    @Embedded val nutrimentLog: NutrimentLog,
    @Relation(
        parentColumn = "nutrimentId",
        entityColumn = "uid"
    )
    val nutrition: Nutrition
)
