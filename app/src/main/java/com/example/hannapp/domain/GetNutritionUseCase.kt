package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutritionRepository
import javax.inject.Inject

class GetNutritionUseCase @Inject constructor(
    private val repository: NutritionRepository,
) {
    suspend operator fun invoke(id: Int) = repository.get(id)
}
