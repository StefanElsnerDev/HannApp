package com.example.hannapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.dropdown.DropDownDialog
import com.example.hannapp.ui.dropdown.SimpleDropDownItem
import com.example.hannapp.ui.dropdown.SimpleDropDownMenu
import com.example.hannapp.ui.input.NutritionDataGroup
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUpdateUiState
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDataUpdateContent(
    pagingItems: LazyPagingItems<NutritionModel>,
    uiState: NutritionUpdateUiState,
    onItemSelected: (NutritionModel) -> Unit,
    onDeleteSelected: (NutritionModel) -> Unit,
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
            var selectedItem by rememberSaveable { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }

            SimpleDropDownMenu(
                selected = selectedItem,
                isExpanded = expanded
            ) {
                expanded = true
            }

            if (expanded) {
                DropDownDialog(
                    pagingItems = pagingItems,
                    onDismiss = { expanded = false },
                    itemContent = { nutrition ->
                        SimpleDropDownItem(
                            item = nutrition,
                            onClick = {
                                onItemSelected(it)
                                selectedItem = it.toString()
                                expanded = false
                            },
                            onLongClick = { onDeleteSelected(it) }
                        )
                    }
                )
            }

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
    val nutriments = viewModel.nutriments.collectAsLazyPagingItems()

    NutritionDataUpdateContent(
        pagingItems = nutriments,
        uiState = uiState,
        onItemSelected = {
            viewModel.selectItem(it)
        },
        onDeleteSelected = { viewModel.delete(it) },
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
            pagingItems = flowOf(PagingData.from(listOf(NutritionModel()))).collectAsLazyPagingItems(),
            uiState = NutritionUpdateUiState(),
            onItemSelected = {},
            onDeleteSelected = {},
            onComponentValueChange = { _, _ -> },
            onReset = {},
            onUpdate = {},
        )
    }
}
