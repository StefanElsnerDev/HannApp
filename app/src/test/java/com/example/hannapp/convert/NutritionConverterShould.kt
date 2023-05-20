package com.example.hannapp.convert

import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NutritionConverterShould {

    private val nutritionConverter = NutritionConverter

    private val id: Long = 123
    private val name = "Apple"
    private val kcal = 4.567
    private val protein = 9.87

    private val nutritionModel = NutritionModel(id = id, name = name, kcal = kcal, protein = protein)
    private val nutritionEntity = Nutrition(uid = id, name = name, kcal = kcal, protein = protein)
    private val nutritionUiModel = NutritionUiModel(id = id, name = name, kcal = kcal.toString(), protein = protein.toString())

    @Test
    fun convertNutritionModelToEntity(){
        val result = nutritionConverter.model(nutritionModel).toEntity()

        Assertions.assertEquals(nutritionEntity, result)
    }

    @Test
    fun convertNutritionEntityToModel(){
        val result = nutritionConverter.entity(nutritionEntity).toModel()

        Assertions.assertEquals(nutritionModel, result)
    }

    @Test
    fun convertUiNutritionModelToEntity(){
        val result = nutritionConverter.uiModel(nutritionUiModel).toEntity()

        Assertions.assertEquals(nutritionEntity, result)
    }

    @Test
    fun convertEntityToUiNutritionModel(){
        val result = nutritionConverter.entity(nutritionEntity).toUiModel()

        Assertions.assertEquals(nutritionUiModel, result)
    }
}