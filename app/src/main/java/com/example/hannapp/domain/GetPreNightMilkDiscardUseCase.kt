package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import javax.inject.Inject

class GetPreNightMilkDiscardUseCase @Inject constructor(
    private val nutrimentLogValidationRepository: NutrimentLogValidationRepository
) {
    operator fun invoke() = nutrimentLogValidationRepository.calculatePreNightMilkDiscard()
}
