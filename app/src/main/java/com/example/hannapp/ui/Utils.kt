package com.example.hannapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.hannapp.R
import com.example.hannapp.data.distinct.NutritionDataComponent
import com.example.hannapp.data.model.NutritionUiModel

fun mapComponentToModelProperty(
    type: NutritionDataComponent,
    model: NutritionUiModel
) = when (type) {
    NutritionDataComponent.NAME -> model.name
    NutritionDataComponent.KCAL -> model.kcal
    NutritionDataComponent.PROTEIN -> model.protein
    NutritionDataComponent.FAT -> model.fat
    NutritionDataComponent.CARBOHYDRATES -> model.carbohydrates
    NutritionDataComponent.SUGAR -> model.sugar
    NutritionDataComponent.FIBER -> model.fiber
    NutritionDataComponent.ALCOHOL -> model.alcohol
}

@Composable
fun supportOnError(isError: Boolean) =
    if (isError) stringResource(id = R.string.fill_field) else ""
