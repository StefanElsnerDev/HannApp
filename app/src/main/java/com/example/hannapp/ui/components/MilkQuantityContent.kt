package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.input.MilkInputFields
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract

@Composable
fun MilkQuantityContent(
    totalState: NutritionLimitContract.ReferenceState.State,
    preNightState: NutritionLimitContract.ReferenceState.State,
    nightState: NutritionLimitContract.ReferenceState.State,
    isCompactScreen: Boolean,
    event: (NutritionLimitContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = PADDING),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.milk_quantities),
            modifier = Modifier.padding(horizontal = PADDING)
        )

        when (isCompactScreen) {
            true -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MilkInputFields(totalState, event, preNightState, nightState)
                }
            }

            false -> {
                Row(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MilkInputFields(totalState, event, preNightState, nightState)
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
fun MilkQuantityGroup_Preview() {
    HannAppTheme {
        MilkQuantityContent(
            totalState = NutritionLimitContract.ReferenceState.State(
                value = "100.5",
                isError = false
            ),
            preNightState = NutritionLimitContract.ReferenceState.State(
                value = "50.50",
                isError = false
            ),
            nightState = NutritionLimitContract.ReferenceState.State(
                value = "abd",
                isError = true
            ),
            isCompactScreen = false,
            event = {}
        )
    }
}

@Preview(
    device = "spec:width=480dp,height=1200dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun MilkQuantityGroup_Compact_Preview() {
    HannAppTheme {
        MilkQuantityContent(
            totalState = NutritionLimitContract.ReferenceState.State(
                value = "100.5",
                isError = false
            ),
            preNightState = NutritionLimitContract.ReferenceState.State(
                value = "50.50",
                isError = false
            ),
            nightState = NutritionLimitContract.ReferenceState.State(
                value = "abd",
                isError = true
            ),
            isCompactScreen = true,
            event = {}
        )
    }
}
