package com.example.hannapp.data.model

import androidx.room.ColumnInfo
import com.example.hannapp.Constants

data class Food(
    val uid: Int,
    @ColumnInfo(name = Constants.FOOD_NAME) val name: String
)
