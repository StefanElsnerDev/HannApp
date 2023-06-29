package com.example.hannapp.domain

import com.example.hannapp.data.repository.NutrimentLogValidationRepository
import com.example.hannapp.utils.round
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPreNightMaltoSubstitutionUseCase @Inject constructor(
    private val nutrimentLogValidationRepository: NutrimentLogValidationRepository
) {
    operator fun invoke() = nutrimentLogValidationRepository.calculatePreNightMaltodextrinSubstitution().map {
        it.round()
    }
}
