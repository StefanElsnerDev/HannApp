package com.example.hannapp.ui.input

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.mapComponentToModelProperty
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun NutritionDataGroup(
    nutritionUiModel: NutritionUiModel,
    errors: Set<NutritionDataComponent>,
    uiComponents: List<NutritionComponent>,
    showErrors: Boolean,
    onReset: (NutritionDataComponent) -> Unit,
    onComponentValueChange: (NutritionComponent, String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(300.dp)
    ) {

        items(uiComponents) { component ->

            val isError = errors.contains(component.type)  && showErrors

            InputField(
                value = mapComponentToModelProperty(component.type, nutritionUiModel),
                onValueChange = {
                    onComponentValueChange(component, it)
                    onReset(component.type)
                },
                modifier = Modifier.padding(12.dp),
                label = component.text,
                isError = isError,
                supportingText = if (isError) {
                    stringResource(id = R.string.fill_field)
                } else {
                    ""
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun FoodDataGroup_Preview_Portrait_LightMode() {
    HannAppTheme {
        NutritionDataGroup(
            nutritionUiModel = NutritionUiModel(123, "Apple", "123 kcal"),
            uiComponents = listOf(
                Kcal(),
                Protein(),
                Fat(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol()
            ),
            errors = emptySet(),
            showErrors = false,
            onReset = {}
        ) { _, _ -> }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun FoodDataGroup_Preview_LandScape_LightMode() {
    HannAppTheme {
        NutritionDataGroup(
            nutritionUiModel = NutritionUiModel(123, "Apple", "123 kcal"),
            uiComponents = listOf(
                Kcal(),
                Protein(),
                Fat(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol()
            ),
            errors = emptySet(),
            showErrors = false,
            onReset = {}
        ) { _, _ -> }
    }
}
