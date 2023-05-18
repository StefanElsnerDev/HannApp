package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.repository.NutrimentLogRepository
import javax.inject.Inject

class UpdateNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    suspend fun update(nutrimentUiLogModel: NutrimentUiLogModel) =
        nutrimentLogRepository.update(nutrimentUiLogModel = nutrimentUiLogModel)
}
