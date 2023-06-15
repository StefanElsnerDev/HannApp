package com.example.hannapp.data.repository

import com.example.hannapp.data.model.NutritionReferences
import com.example.hannapp.data.model.NutritionUiReferences
import com.example.hannapp.data.source.NutritionReferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutritionReferenceRepository @Inject constructor(
    private val nutritionReferenceDataSource: NutritionReferenceDataSource
) {

    suspend fun save(nutritionUiReferences: NutritionUiReferences): Int {
        return nutritionReferenceDataSource.save(
            nutritionUiReferences.let {
                NutritionReferences(
                    kcal = it.kcal,
                    protein = it.protein,
                    carbohydrates = it.carbohydrates,
                    fat = it.fat
                )
            }
        )
    }

    fun emitReference(): Flow<NutritionUiReferences> =
        nutritionReferenceDataSource.emitReferences().map {
            require(it.kcal != null)
            require(it.protein != null)
            require(it.carbohydrates != null)
            require(it.fat != null)

            NutritionUiReferences(
                kcal = it.kcal,
                protein = it.protein,
                carbohydrates = it.carbohydrates,
                fat = it.fat
            )
        }
}
