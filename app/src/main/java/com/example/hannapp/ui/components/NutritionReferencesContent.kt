package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.supportOnError
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import com.example.hannapp.ui.viewmodel.NutritionReference

@Composable
fun NutritionReferencesContent(
    kcalState: NutritionLimitContract.ReferenceState.State,
    proteinState: NutritionLimitContract.ReferenceState.State,
    carbohydratesState: NutritionLimitContract.ReferenceState.State,
    fatState: NutritionLimitContract.ReferenceState.State,
    event: (NutritionLimitContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = PADDING),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.nutrition_references),
            modifier = Modifier.padding(horizontal = PADDING)
        )

        Row(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
                        .padding(horizontal = PADDING)
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
                        .padding(horizontal = PADDING)
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
                        .padding(horizontal = PADDING)
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
                        .padding(horizontal = PADDING)
                )
            }
        }
    }
}

@Preview(
    device = "spec:width=2280dp,height=800dp,dpi=240,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NutritionReferencesContent_Preview() {
    HannAppTheme {
        NutritionReferencesContent(
            kcalState = NutritionLimitContract.ReferenceState.State(
                value = "100.5",
                isError = false
            ),
            proteinState = NutritionLimitContract.ReferenceState.State(
                value = "50.50",
                isError = false
            ),
            carbohydratesState = NutritionLimitContract.ReferenceState.State(
                value = "abd",
                isError = true
            ),
            fatState = NutritionLimitContract.ReferenceState.State(
                value = "34.5",
                isError = false
            ),
            event = {},
            modifier = Modifier.wrapContentSize()
        )
    }
}
