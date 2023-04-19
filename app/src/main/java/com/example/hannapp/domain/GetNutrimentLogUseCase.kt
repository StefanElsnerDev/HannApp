package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.repository.NutrimentLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    fun observeNutrimentLog(): Flow<List<NutrimentLogModel>> = nutrimentLogRepository.getLogs()
}
