package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.repository.NutrimentLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogRepository: NutrimentLogRepository
) {
    operator fun invoke(): Flow<List<NutrimentUiLogModel>> =
        nutrimentLogRepository.getLogs().map { logModels ->
            logModels.map {
                it.let {
                    NutrimentUiLogModel(
                        id = it.id,
                        nutrition = NutritionConverter.entity(it.nutrition).toUiModel(),
                        quantity = it.quantity,
                        unit = "g / ml",
                        createdAt = it.createdAt,
                        modifiedAt = it.modifiedAt
                    )
                }
            }
        }
}
