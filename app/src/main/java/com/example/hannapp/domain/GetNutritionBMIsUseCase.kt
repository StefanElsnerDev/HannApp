package com.example.hannapp.domain

import com.example.hannapp.data.model.entity.NutritionBMI
import com.example.hannapp.data.repository.NutritionRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetNutritionBMIsUseCase @Inject constructor(
    private val repository: NutritionRepository,
) {
    operator fun invoke() = repository.getNames()

    suspend fun insertDummy() = repository.insert(NutritionBMI(name = LocalDateTime.now().toString()))
}
