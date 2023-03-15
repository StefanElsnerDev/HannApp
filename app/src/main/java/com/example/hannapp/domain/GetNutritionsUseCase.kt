package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutritionRepository
import javax.inject.Inject

class GetNutritionsUseCase @Inject constructor(
    private val repository: NutritionRepository,
) {
    operator fun invoke() = repository.getNames()
}
