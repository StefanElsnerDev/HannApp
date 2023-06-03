package com.example.hannapp.data.repository

import com.example.hannapp.data.model.NutritionReferences
import com.example.hannapp.data.source.NutritionReferenceDataSource
import javax.inject.Inject

class NutritionReferenceRepository @Inject constructor(
    private val nutritionReferenceDataSource: NutritionReferenceDataSource
) {
    suspend fun save(nutritionReferences: NutritionReferences): Int {
        val kcal = nutritionReferences.kcal
        val protein = nutritionReferences.protein
        val carbohydrates = nutritionReferences.carbohydrates
        val fat = nutritionReferences.fat

        require(kcal != null)
        require(protein != null)
        require(carbohydrates != null)
        require(fat != null)

        return nutritionReferenceDataSource.save(
            nutritionReferences.copy(
                kcal = kcal,
                protein = protein,
                carbohydrates = carbohydrates,
                fat = fat
            )
        )
    }

    fun emitReference() = nutritionReferenceDataSource.emitReferences()
}
