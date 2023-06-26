package com.example.hannapp.ui.input

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.hannapp.R
import com.example.hannapp.ui.supportOnError
import com.example.hannapp.ui.theme.Constraints
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import com.example.hannapp.ui.viewmodel.NutritionReference

@Composable
fun NutritionInputFields(
    kcalState: NutritionLimitContract.ReferenceState.State,
    event: (NutritionLimitContract.Event) -> Unit,
    proteinState: NutritionLimitContract.ReferenceState.State,
    carbohydratesState: NutritionLimitContract.ReferenceState.State,
    fatState: NutritionLimitContract.ReferenceState.State,
    keyboardActions: KeyboardActions,
    lastImeAction: ImeAction
) {
    kcalState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnNutritionUpdate(
                        nutritionReference = NutritionReference.KCAL,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.energy),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Next
        )
    }

    proteinState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnNutritionUpdate(
                        nutritionReference = NutritionReference.PROTEIN,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.protein),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Next
        )
    }

    carbohydratesState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnNutritionUpdate(
                        nutritionReference = NutritionReference.CARBOHYDRATES,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.carbohydrates),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Next
        )
    }

    fatState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnNutritionUpdate(
                        nutritionReference = NutritionReference.FAT,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.fat),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = lastImeAction
        )
    }
}
