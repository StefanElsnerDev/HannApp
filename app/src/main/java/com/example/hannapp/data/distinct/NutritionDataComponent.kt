package com.example.hannapp.data.distinct

import com.example.hannapp.Constants
import com.example.hannapp.ui.viewmodel.NutritionComponentState

enum class NutritionDataComponent(val text: String) {
    NAME(Constants.FOOD_NAME),
    KCAL(Constants.KCAL),
    PROTEIN(Constants.PROTEIN),
    FAD(Constants.FAD),
    CARBOHYDRATES(Constants.CARBOHYDRATES),
    SUGAR(Constants.SUGAR),
    FIBER(Constants.FIBER),
    ALCOHOL(Constants.ALCOHOL),
    ENERGY(Constants.ENERGY)
}

interface NutritionComponent {
    var type: NutritionDataComponent
    var text: String
    fun update(nutritionComponentState: NutritionComponentState, value: String): NutritionComponentState
}

class Name : NutritionComponent {
    override var type = NutritionDataComponent.NAME
    override var text = "name"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(name = value)
}

class Kcal : NutritionComponent {
    override var type = NutritionDataComponent.KCAL
    override var text = "Kcal"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(kcal = value)
}

class Protein : NutritionComponent {
    override var type = NutritionDataComponent.PROTEIN
    override var text = "Protein"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(protein = value)
}

class Fad : NutritionComponent {
    override var type = NutritionDataComponent.FAD
    override var text = "Fad"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fad = value)
}

class Carbohydrates : NutritionComponent {
    override var type = NutritionDataComponent.CARBOHYDRATES
    override var text = "Carbohydrates"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(carbohydrates = value)
}

class Sugar : NutritionComponent {
    override var type = NutritionDataComponent.SUGAR
    override var text = "Sugar"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(sugar = value)
}

class Fiber : NutritionComponent {
    override var type = NutritionDataComponent.FIBER
    override var text = "Fiber"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fiber = value)
}

class Alcohol : NutritionComponent {
    override var type = NutritionDataComponent.ALCOHOL
    override var text = "Alcohol"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(alcohol = value)
}

class Energy : NutritionComponent {
    override var type = NutritionDataComponent.ENERGY
    override var text = "Energy"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(energy = value)
}