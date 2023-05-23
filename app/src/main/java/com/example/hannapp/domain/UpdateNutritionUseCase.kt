package com.example.hannapp.domain

import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.repository.NutritionRepository
import javax.inject.Inject

class UpdateNutritionUseCase @Inject constructor(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(nutritionUiModel: NutritionUiModel) =
        repository.update(nutritionUiModel = nutritionUiModel)
}
