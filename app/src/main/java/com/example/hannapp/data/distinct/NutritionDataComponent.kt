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

private fun addError(
    componentState: NutritionComponentState,
    component: NutritionDataComponent
): NutritionComponentState {
    val errors = componentState.error.toMutableSet()
    errors.add(component)
    return componentState.copy(error = errors.toList())
}

private fun removeError(
    nutritionComponentState: NutritionComponentState,
    component: NutritionDataComponent
): NutritionComponentState {
    val errors = nutritionComponentState.error.toMutableSet()
    errors.remove(component)
    return nutritionComponentState.copy(error = errors.toList())
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
        return if (nutritionComponentState.name.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.NAME)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.NAME)
        }
    }
}

class Kcal : NutritionComponent {
    override var type = NutritionDataComponent.KCAL
    override var text = "Kcal"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(kcal = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.kcal.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.KCAL)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.KCAL)
        }
    }
}

class Protein : NutritionComponent {
    override var type = NutritionDataComponent.PROTEIN
    override var text = "Protein"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(protein = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.protein.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.PROTEIN)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.PROTEIN)
        }
    }
}

class Fad : NutritionComponent {
    override var type = NutritionDataComponent.FAD
    override var text = "Fad"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fad = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.fad.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.FAD)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.FAD)
        }
    }
}

class Carbohydrates : NutritionComponent {
    override var type = NutritionDataComponent.CARBOHYDRATES
    override var text = "Carbohydrates"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(carbohydrates = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.carbohydrates.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.CARBOHYDRATES)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.CARBOHYDRATES)
        }
    }
}

class Sugar : NutritionComponent {
    override var type = NutritionDataComponent.SUGAR
    override var text = "Sugar"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(sugar = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.sugar.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.SUGAR)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.SUGAR)
        }
    }
}

class Fiber : NutritionComponent {
    override var type = NutritionDataComponent.FIBER
    override var text = "Fiber"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(fiber = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.fiber.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.FIBER)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.FIBER)
        }
    }
}

class Alcohol : NutritionComponent {
    override var type = NutritionDataComponent.ALCOHOL
    override var text = "Alcohol"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(alcohol = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.alcohol.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.ALCOHOL)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.ALCOHOL)
        }
    }
}

class Energy : NutritionComponent {
    override var type = NutritionDataComponent.ENERGY
    override var text = "Energy"

    override fun update(nutritionComponentState: NutritionComponentState, value: String) =
        nutritionComponentState.copy(energy = value)

    override fun validate(nutritionComponentState: NutritionComponentState): NutritionComponentState {
        return if (nutritionComponentState.energy.isBlank()) {
            addError(nutritionComponentState, NutritionDataComponent.ENERGY)
        } else {
            removeError(nutritionComponentState, NutritionDataComponent.ENERGY)
        }
    }
}
