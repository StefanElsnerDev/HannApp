package com.example.hannapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.R
import com.example.hannapp.data.distinct.*
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.*

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDataContent(
    navController: NavHostController = rememberNavController(),
    uiState: NutritionComponentState = NutritionComponentState(),
    uiComponents: List<NutritionComponent> = listOf(
        Kcal(),
        Protein(),
        Fad(),
        Carbohydrates(),
        Sugar(),
        Fiber(),
        Alcohol(),
        Energy()
    ),
    onComponentValueChange: (NutritionComponent, String) -> Unit = { _, _ -> },
    onReset: (NutritionDataComponent) -> Unit = { _ -> },
    onAdd: () -> Unit = {}
) {
    HannAppTheme {
        AppScaffold(
            bottomBar = { NavigationBar(navController) },
            floatingActionButton = {
                FAB(imageVector = Icons.Filled.Add) { onAdd() }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputField(
                    value = uiState.name,
                    onValueChange = { onComponentValueChange(Name(), it) },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    label = stringResource(id = R.string.food_name)
                )
                NutritionDataGroup(
                    onComponentValueChange = onComponentValueChange,
                    onReset = onReset,
                    uiComponents = uiComponents,
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
fun NutritionDataScreen(
    viewModel: NutritionDataViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiComponents by viewModel.uiComponents.collectAsState()

    NutritionDataContent(
        navController = navController,
        uiState = uiState,
        uiComponents = uiComponents,
        onComponentValueChange = { updateStrategy, value ->
            viewModel.onNutritionTypeChange(
                updateStrategy,
                value
            )
            viewModel.validate()
        },
        onReset = { viewModel.resetError(it) },
        onAdd = {
            if (uiState.isValid) viewModel.insert()
            else viewModel.validate()
        }
    )
}

@Composable
fun NutritionDataGroup(
    uiState: NutritionComponentState,
    uiComponents: List<NutritionComponent>,
    onReset: (NutritionDataComponent) -> Unit,
    onComponentValueChange: (NutritionComponent, String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(300.dp)
    ) {

        items(uiComponents) { component ->

            val isError = uiState.error.contains(component.type)

            InputField(
                value = uiState.toUiState(component.type),
                onValueChange = {
                    onComponentValueChange(component, it)
                    onReset(component.type)
                },
                modifier = Modifier,
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

private fun NutritionComponentState.toUiState(
    type: NutritionDataComponent
) = when (type) {
    NutritionDataComponent.NAME -> name
    NutritionDataComponent.KCAL -> kcal
    NutritionDataComponent.PROTEIN -> protein
    NutritionDataComponent.FAD -> fad
    NutritionDataComponent.CARBOHYDRATES -> carbohydrates
    NutritionDataComponent.SUGAR -> sugar
    NutritionDataComponent.FIBER -> fiber
    NutritionDataComponent.ALCOHOL -> alcohol
    NutritionDataComponent.ENERGY -> energy
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun FoodDataGroup_Preview_Portrait_LightMode() {
    HannAppTheme {
        NutritionDataGroup(
            uiState = NutritionComponentState(),
            uiComponents = listOf(
                Kcal(),
                Protein(),
                Fad(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol(),
                Energy()
            ),
            onReset = {}
        ) { _, _ -> }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun FoodDataGroup_Preview_LandScape_LightMode(
    modifier: Modifier = Modifier
) {
    HannAppTheme {
        NutritionDataGroup(
            uiState = NutritionComponentState(),
            uiComponents = listOf(
                Kcal(),
                Protein(),
                Fad(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol(),
                Energy()
            ),
            onReset = {}
        ) { _, _ -> }
    }
}
