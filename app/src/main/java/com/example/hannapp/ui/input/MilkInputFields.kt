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
import com.example.hannapp.ui.viewmodel.MilkReference
import com.example.hannapp.ui.viewmodel.NutritionLimitContract

@Composable
fun MilkInputFields(
    totalState: NutritionLimitContract.ReferenceState.State,
    event: (NutritionLimitContract.Event) -> Unit,
    preNightState: NutritionLimitContract.ReferenceState.State,
    nightState: NutritionLimitContract.ReferenceState.State,
    keyboardActions: KeyboardActions,
    imeActionOnLast: ImeAction
) {
    totalState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnMilkUpdate(
                        milkReference = MilkReference.TOTAL,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.total),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Next
        )
    }

    preNightState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnMilkUpdate(
                        milkReference = MilkReference.PRE_NIGHT,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.share_preNight),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Next
        )
    }

    nightState.apply {
        InputField(
            value = value,
            onValueChange = {
                event(
                    NutritionLimitContract.Event.OnMilkUpdate(
                        milkReference = MilkReference.NIGHT,
                        value = it
                    )
                )
            },
            label = stringResource(id = R.string.share_night),
            isError = isError,
            supportingText = supportOnError(isError = isError),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = Constraints.PADDING),
            keyboardActions = keyboardActions,
            imeAction = imeActionOnLast
        )
    }
}
