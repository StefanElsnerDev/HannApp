package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.ui.mood.Mood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateNutrimentLogUseCase @Inject constructor(
    private val nutrimentLogValidationRepository: NutrimentLogValidationRepository
) {
    operator fun invoke(): Flow<Mood> = nutrimentLogValidationRepository.validatePreNight()
}
