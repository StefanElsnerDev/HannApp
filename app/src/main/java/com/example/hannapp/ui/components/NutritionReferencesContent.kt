package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.input.NutritionInputFields
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract

@Composable
fun NutritionReferencesContent(
    kcalState: NutritionLimitContract.ReferenceState.State,
    proteinState: NutritionLimitContract.ReferenceState.State,
    carbohydratesState: NutritionLimitContract.ReferenceState.State,
    fatState: NutritionLimitContract.ReferenceState.State,
    isCompactScreen: Boolean,
    event: (NutritionLimitContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.nutrition_references),
            modifier = Modifier.padding(horizontal = PADDING)
        )

        when (isCompactScreen) {
            true -> {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NutritionInputFields(
                        kcalState,
                        event,
                        proteinState,
                        carbohydratesState,
                        fatState
                    )
                }
            }

            false -> {
                Row(
                    modifier = modifier.width(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NutritionInputFields(
                        kcalState,
                        event,
                        proteinState,
                        carbohydratesState,
                        fatState
                    )
                }
            }
        }
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape",
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
            isCompactScreen = false,
            event = {},
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview(
    device = "spec:width=480dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NutritionReferencesContent_Compact_Preview() {
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
            isCompactScreen = true,
            event = {},
            modifier = Modifier.wrapContentSize()
        )
    }
}
