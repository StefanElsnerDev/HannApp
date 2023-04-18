package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentLogModel
import javax.inject.Inject

class InsertNutrimentLogUseCase @Inject constructor() {
    suspend operator fun invoke(nutrimentLogModel: NutrimentLogModel): Boolean = false
}
