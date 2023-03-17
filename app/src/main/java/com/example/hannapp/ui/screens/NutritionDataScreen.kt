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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.R
import com.example.hannapp.data.distinct.NutritionComponent
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionComponentState
import com.example.hannapp.ui.viewmodel.NutritionDataViewModel

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDataContent(
    navController: NavHostController = rememberNavController(),
    uiState: NutritionComponentState = NutritionComponentState(),
    onComponentValueChange: (NutritionComponent, String) -> Unit = { _, _ -> },
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
                    onValueChange = { onComponentValueChange(NutritionComponent.NAME, it) },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    label = stringResource(id = R.string.food_name)
                )
                NutritionDataGroup(
                    onComponentValueChange = onComponentValueChange,
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

    NutritionDataContent(
        navController = navController,
        uiState = uiState,
        onComponentValueChange = { nutritionType, value ->
            viewModel.onNutritionTypeChange(
                nutritionType,
                value
            )
        },
        onAdd = { viewModel.insert() }
    )
}

@Composable
fun NutritionDataGroup(
    uiState: NutritionComponentState,
    onComponentValueChange: (NutritionComponent, String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(300.dp)
    ) {
        val components = listOf(
            NutritionComponent.KCAL,
            NutritionComponent.PROTEIN,
            NutritionComponent.FAD,
            NutritionComponent.CARBOHYDRATES,
            NutritionComponent.SUGAR,
            NutritionComponent.FIBER,
            NutritionComponent.ALCOHOL,
            NutritionComponent.ENERGY
        )
        items(components) { component ->
            val nutritionValue = uiState.toUiState(component)

            InputField(
                value = nutritionValue,
                onValueChange = { onComponentValueChange(component, it) },
                modifier = Modifier,
                label = component.text,
            )
        }
    }
}

private fun NutritionComponentState.toUiState(
    type: NutritionComponent
) = when (type) {
    NutritionComponent.NAME -> name
    NutritionComponent.KCAL -> kcal
    NutritionComponent.PROTEIN -> protein
    NutritionComponent.FAD -> fad
    NutritionComponent.CARBOHYDRATES -> carbohydrates
    NutritionComponent.SUGAR -> sugar
    NutritionComponent.FIBER -> fiber
    NutritionComponent.ALCOHOL -> alcohol
    NutritionComponent.ENERGY -> energy
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
            uiState = NutritionComponentState()
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
            uiState = NutritionComponentState()
        ) { _, _ -> }
    }
}
