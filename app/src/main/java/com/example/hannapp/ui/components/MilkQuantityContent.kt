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
import com.example.hannapp.ui.viewmodel.MilkReference
import com.example.hannapp.ui.viewmodel.NutritionLimitContract

@Composable
fun MilkQuantityContent(
    totalState: NutritionLimitContract.ReferenceState.State,
    preNightState: NutritionLimitContract.ReferenceState.State,
    nightState: NutritionLimitContract.ReferenceState.State,
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

        Row(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
                        .padding(horizontal = PADDING)
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
                        .padding(horizontal = PADDING)
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
                        .padding(horizontal = PADDING)
                )
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
            event = {},
            modifier = Modifier.wrapContentSize()
        )
    }
}
