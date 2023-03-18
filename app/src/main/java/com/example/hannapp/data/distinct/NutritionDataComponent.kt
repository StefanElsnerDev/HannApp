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
    fun update(
        nutritionComponentState: NutritionComponentState,
        value: String
    ): NutritionComponentState

    fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState
}

class Name : NutritionComponent {
    override var type = NutritionDataComponent.NAME
    override var text = "name"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(name = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.name.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.NAME)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Kcal : NutritionComponent {
    override var type = NutritionDataComponent.KCAL
    override var text = "Kcal"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(kcal = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.kcal.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.KCAL)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Protein : NutritionComponent {
    override var type = NutritionDataComponent.PROTEIN
    override var text = "Protein"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(protein = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.protein.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.PROTEIN)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Fad : NutritionComponent {
    override var type = NutritionDataComponent.FAD
    override var text = "Fad"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fad = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.fad.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.FAD)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Carbohydrates : NutritionComponent {
    override var type = NutritionDataComponent.CARBOHYDRATES
    override var text = "Carbohydrates"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(carbohydrates = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.carbohydrates.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.CARBOHYDRATES)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Sugar : NutritionComponent {
    override var type = NutritionDataComponent.SUGAR
    override var text = "Sugar"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(sugar = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.sugar.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.SUGAR)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Fiber : NutritionComponent {
    override var type = NutritionDataComponent.FIBER
    override var text = "Fiber"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fiber = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.fiber.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.FIBER)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Alcohol : NutritionComponent {
    override var type = NutritionDataComponent.ALCOHOL
    override var text = "Alcohol"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(alcohol = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.alcohol.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.ALCOHOL)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}

class Energy : NutritionComponent {
    override var type = NutritionDataComponent.ENERGY
    override var text = "Energy"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(energy = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.energy.isEmpty()) {
            val errors = nutritionComponentState.error.toMutableList()
            errors.add(NutritionDataComponent.ENERGY)

            nutritionComponentState.copy(error = errors)
        } else {
            nutritionComponentState
        }
    }
}