package com.example.hannapp.domain

import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import javax.inject.Inject

class InsertNutritionUseCase @Inject constructor(
    private val repository: NutritionRepository,
) {
    suspend operator fun invoke(nutrition: Nutrition) = repository.insert(nutrition)
}
