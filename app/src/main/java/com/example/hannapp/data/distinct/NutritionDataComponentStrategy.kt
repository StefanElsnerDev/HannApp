package com.example.hannapp.data.distinct

import com.example.hannapp.data.model.NutritionUiModel

private fun addError(
    errorCollection: Set<NutritionDataComponent>,
    component: NutritionDataComponent
): Set<NutritionDataComponent> {
    val errors = errorCollection.toMutableSet()
    errors.add(component)
    return errors.toSet()
}

private fun removeError(
    errorCollection: Set<NutritionDataComponent>,
    component: NutritionDataComponent
): Set<NutritionDataComponent> {
    val errors = errorCollection.toMutableSet()
    errors.remove(component)
    return errors.toSet()
}

interface NutritionComponent {
    var type: NutritionDataComponent
    var text: String
    fun update(
        nutritionUiModel: NutritionUiModel,
        value: String
    ): NutritionUiModel

    fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent>
}

class Name : NutritionComponent {
    override var type = NutritionDataComponent.NAME
    override var text = "name"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(name = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.name.isBlank()) {
            addError(errors, NutritionDataComponent.NAME)
        } else {
            removeError(errors, NutritionDataComponent.NAME)
        }
    }
}

class Kcal : NutritionComponent {
    override var type = NutritionDataComponent.KCAL
    override var text = "Kcal"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(kcal = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.kcal.isBlank()) {
            addError(errors, NutritionDataComponent.KCAL)
        } else {
            removeError(errors, NutritionDataComponent.KCAL)
        }
    }
}

class Protein : NutritionComponent {
    override var type = NutritionDataComponent.PROTEIN
    override var text = "Protein"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(protein = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.protein.isBlank()) {
            addError(errors, NutritionDataComponent.PROTEIN)
        } else {
            removeError(errors, NutritionDataComponent.PROTEIN)
        }
    }
}

class Fat : NutritionComponent {
    override var type = NutritionDataComponent.FAT
    override var text = "Fat"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(fat = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.fat.isBlank()) {
            addError(errors, NutritionDataComponent.FAT)
        } else {
            removeError(errors, NutritionDataComponent.FAT)
        }
    }
}

class Carbohydrates : NutritionComponent {
    override var type = NutritionDataComponent.CARBOHYDRATES
    override var text = "Carbohydrates"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(carbohydrates = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.carbohydrates.isBlank()) {
            addError(errors, NutritionDataComponent.CARBOHYDRATES)
        } else {
            removeError(errors, NutritionDataComponent.CARBOHYDRATES)
        }
    }
}

class Sugar : NutritionComponent {
    override var type = NutritionDataComponent.SUGAR
    override var text = "Sugar"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(sugar = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.sugar.isBlank()) {
            addError(errors, NutritionDataComponent.SUGAR)
        } else {
            removeError(errors, NutritionDataComponent.SUGAR)
        }
    }
}

class Fiber : NutritionComponent {
    override var type = NutritionDataComponent.FIBER
    override var text = "Fiber"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(fiber = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.fiber.isBlank()) {
            addError(errors, NutritionDataComponent.FIBER)
        } else {
            removeError(errors, NutritionDataComponent.FIBER)
        }
    }
}

class Alcohol : NutritionComponent {
    override var type = NutritionDataComponent.ALCOHOL
    override var text = "Alcohol"

    override fun update(nutritionUiModel: NutritionUiModel, value: String) =
        nutritionUiModel.copy(alcohol = value)

    override fun validate(
        nutritionUiModel: NutritionUiModel,
        errors: Set<NutritionDataComponent>
    ): Set<NutritionDataComponent> {
        return if (nutritionUiModel.alcohol.isBlank()) {
            addError(errors, NutritionDataComponent.ALCOHOL)
        } else {
            removeError(errors, NutritionDataComponent.ALCOHOL)
        }
    }
}
