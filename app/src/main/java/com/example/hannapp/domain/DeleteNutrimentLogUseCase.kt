package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogRepository
import javax.inject.Inject

class DeleteNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    suspend fun clear(): Boolean = nutrimentLogRepository.clearLog()
}
