package com.example.hannapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hannapp.R
import com.example.hannapp.data.distinct.*
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.selection.DropDownField
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUpdateUiState
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDataUpdateContent(
    items: List<String>,
    uiState: NutritionUpdateUiState,
    onItemSelected: (Int) -> Unit,
    onComponentValueChange: (NutritionComponent, String) -> Unit,
    onReset: (NutritionDataComponent) -> Unit,
    onUpdate: () -> Unit
) {
    AppScaffold(
        floatingActionButton = {
            FAB({ Icon(painterResource(id = R.drawable.change), "") }) { onUpdate() }
        }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropDownField(
                modifier = Modifier.fillMaxWidth(),
                items = items,
            ) { onItemSelected(it) }
            NutritionDataGroup(
                nutritionModel = uiState.nutritionModel,
                onComponentValueChange = onComponentValueChange,
                onReset = onReset,
                uiComponents = uiState.components,
                errors = uiState.errors,
                showErrors = false
            )
        }
    }
}

@Composable
fun NutritionDataUpdateScreen(
    viewModel: NutritionUpdateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NutritionDataUpdateContent(
        items = uiState.foodList.map { it.name },
        uiState = uiState,
        onItemSelected = {
            viewModel.currentListIndex = it
            viewModel.selectItem(it)
        },
        onComponentValueChange = { component, value ->
            viewModel.onNutritionChange(
                component,
                value
            )
        },
        onReset = {},
        onUpdate = { viewModel.update() }
    )
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NutritionDataUpdate_LightMode() {
    HannAppTheme {
        NutritionDataUpdateContent(
            items = emptyList(),
            uiState = NutritionUpdateUiState(),
            onItemSelected = {},
            onComponentValueChange = { _, _ -> },
            onReset = {},
            onUpdate = {},
        )
    }
}
