package com.example.hannapp.domain

import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.data.repository.NutritionReferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveNutritionReferencesUseCase @Inject constructor(
    private val nutritionReferenceRepository: NutritionReferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(nutritionLimitReferenceUiModel: NutritionLimitReferenceUiModel) {
        withContext(dispatcher) {
            nutritionReferenceRepository.save(
                nutritionLimitReferenceUiModel.let {
                    NutritionUiReferences(
                        kcal = it.kcal.toDouble(),
                        protein = it.protein.toDouble(),
                        carbohydrates = it.protein.toDouble(),
                        fat = it.fat.toDouble()
                    )
                }
            )
        }
    }
}
