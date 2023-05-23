package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogRepository
import javax.inject.Inject

class InsertNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    suspend operator fun invoke(nutrimentId: Long, quantity: Double): Boolean =
        nutrimentLogRepository.log(
            nutrimentId = nutrimentId,
            quantity = quantity
        ) != -1L
}
