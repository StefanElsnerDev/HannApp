package com.example.hannapp.domain

import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetNutritionReferencesUseCase @Inject constructor() {

    suspend operator fun invoke(): Flow<NutritionLimitReferenceUiModel> = flowOf()
}
