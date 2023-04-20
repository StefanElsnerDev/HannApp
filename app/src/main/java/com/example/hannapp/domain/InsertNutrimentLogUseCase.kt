package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.repository.NutrimentLogRepository
import javax.inject.Inject

class InsertNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    suspend operator fun invoke(nutrimentLogModel: NutrimentLogModel): Boolean{
        val index = nutrimentLogRepository.log(nutrimentLogModel)
        return index != -1L
    }
}
