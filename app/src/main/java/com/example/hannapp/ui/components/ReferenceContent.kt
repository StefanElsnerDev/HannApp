package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionLimitContract

@Composable
fun ReferenceContent(
    uiState: NutritionLimitContract.State,
    event: (NutritionLimitContract.Event) -> Unit,
    isCompactScreen: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.nutrition_references)
            ) {}
        },
        bottomBar = { NavigationBar(navController) },
        floatingActionButton = {
            IconButton(
                onClick = {
                    event(NutritionLimitContract.Event.OnValidate)
                    if (uiState.isDataValid) event(NutritionLimitContract.Event.OnSave)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.change),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.apply {
                Surface(
                    modifier = Modifier.wrapContentWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    NutritionReferencesContent(
                        kcalState = kcal,
                        proteinState = protein,
                        carbohydratesState = carbohydrates,
                        fatState = fat,
                        isCompactScreen = isCompactScreen,
                        event = event
                    )
                }

                Surface(
                    modifier = Modifier.wrapContentWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    MilkQuantityContent(
                        totalState = totalQuantity,
                        preNightState = preNightQuantity,
                        nightState = nightQuantity,
                        event = event
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
fun ReferencesContent_Preview() {
    HannAppTheme {
        ReferenceContent(
            uiState = NutritionLimitContract.State(
                kcal = NutritionLimitContract.ReferenceState.State(
                    value = "112.3",
                    isError = false
                )
            ),
            isCompactScreen = false,
            navController = rememberNavController(),
            event = {}
        )
    }
}
