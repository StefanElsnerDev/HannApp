package com.example.hannapp.domain

import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.data.repository.NutritionReferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNutritionReferencesUseCase @Inject constructor(
    private val nutritionReferenceRepository: NutritionReferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<NutritionLimitReferenceUiModel> =
        nutritionReferenceRepository.emitReference().map {
            NutritionLimitReferenceUiModel(
                kcal = it.kcal.toString(),
                protein = it.protein.toString(),
                carbohydrates = it.carbohydrates.toString(),
                fat = it.fat.toString()
            )
        }.flowOn(
            dispatcher
        )
}
