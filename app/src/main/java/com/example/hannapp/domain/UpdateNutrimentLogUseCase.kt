package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogRepository
import javax.inject.Inject

class UpdateNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    suspend fun update(logId: Long, nutrimentId: Long, quantity: Double) =
        nutrimentLogRepository.update(
            logId = logId,
            nutrimentId = nutrimentId,
            quantity = quantity
        )
}
