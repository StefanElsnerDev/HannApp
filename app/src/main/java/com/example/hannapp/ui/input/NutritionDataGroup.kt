package com.example.hannapp.ui.input

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.data.distinct.Alcohol
import com.example.hannapp.data.distinct.Carbohydrates
import com.example.hannapp.data.distinct.Fat
import com.example.hannapp.data.distinct.Fiber
import com.example.hannapp.data.distinct.Kcal
import com.example.hannapp.data.distinct.NutritionComponent
import com.example.hannapp.data.distinct.NutritionDataComponent
import com.example.hannapp.data.distinct.Protein
import com.example.hannapp.data.distinct.Sugar
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.mapComponentToModelProperty
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun NutritionDataGroup(
    modifier: Modifier = Modifier,
    nutritionUiModel: NutritionUiModel,
    errors: Set<NutritionDataComponent>,
    uiComponents: List<NutritionComponent>,
    showErrors: Boolean,
    onLastItem: () -> Unit = {},
    onReset: (NutritionDataComponent) -> Unit = {},
    onComponentValueChange: (NutritionComponent, String) -> Unit = { _, _ -> }
) {
    Surface(
        modifier = modifier.padding(PADDING),
        shape = MaterialTheme.shapes.medium
    ) {
        val localFocusManager = LocalFocusManager.current

        LazyVerticalGrid(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant).padding(PADDING),
            columns = GridCells.Adaptive(300.dp)
        ) {
            itemsIndexed(uiComponents) { index, component ->
                val imeAction =
                    if (index == uiComponents.lastIndex) ImeAction.Done else ImeAction.Next
                val keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Next)
                    },
                    onDone = {
                        localFocusManager.clearFocus()
                        onLastItem()
                    }
                )
                val isError = errors.contains(component.type) && showErrors

                InputField(
                    value = mapComponentToModelProperty(component.type, nutritionUiModel),
                    onValueChange = {
                        onComponentValueChange(component, it)
                        onReset(component.type)
                    },
                    modifier = Modifier.padding(PADDING),
                    label = component.text,
                    isError = isError,
                    supportingText = if (isError) {
                        stringResource(id = R.string.fill_field)
                    } else {
                        ""
                    },
                    keyboardType = KeyboardType.Number,
                    keyboardActions = keyboardActions,
                    imeAction = imeAction
                )
            }
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
