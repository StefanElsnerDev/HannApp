package com.example.hannapp.convert

import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NutritionConverterShould {

    private lateinit var nutritionConverter: NutritionConverter

    private val nutritionModel = NutritionModel(id = 123, name = "Apple", kcal = "1234.5")
    private val nutritionEntity = Nutrition(uid = 123, name = "Apple", kcal = "1234.5")

    @BeforeEach
    fun beforeEach() {
        nutritionConverter = NutritionConverter()
    }

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
}