package com.example.hannapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.distinct.Alcohol
import com.example.hannapp.data.distinct.Carbohydrates
import com.example.hannapp.data.distinct.Fat
import com.example.hannapp.data.distinct.Fiber
import com.example.hannapp.data.distinct.Kcal
import com.example.hannapp.data.distinct.Name
import com.example.hannapp.data.distinct.NutritionComponent
import com.example.hannapp.data.distinct.NutritionDataComponent
import com.example.hannapp.data.distinct.Protein
import com.example.hannapp.data.distinct.Sugar
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.Dialog
import com.example.hannapp.ui.components.NutrimentCard
import com.example.hannapp.ui.components.SnackBar
import com.example.hannapp.ui.dropdown.DropDownDialog
import com.example.hannapp.ui.dropdown.EmptySelectionDropDownMenu
import com.example.hannapp.ui.input.NutritionDataGroup
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.ComponentUiState
import com.example.hannapp.ui.viewmodel.NutritionUpdateUiState
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun NutritionDataUpdateContent(
    pagingItems: LazyPagingItems<NutritionUiModel>,
    componentUiState: ComponentUiState,
    uiState: NutritionUpdateUiState,
    uiComponents: List<NutritionComponent>,
    onItemSelected: (NutritionUiModel) -> Unit,
    onDeleteSelected: (NutritionUiModel) -> Unit,
    onComponentValueChange: (NutritionComponent, String) -> Unit,
    onReset: (NutritionDataComponent) -> Unit,
    onUpdate: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val error = uiState.errorMessage
    val snackbarHostState = remember { SnackbarHostState() }
    val showUpdateDialog = rememberSaveable { mutableStateOf(false) }

    AppScaffold(
        floatingActionButton = {
            FAB(
                icon = { Icon(painterResource(id = R.drawable.change), "") },
                onClick = { showUpdateDialog.value = true }
            )
        },
        snackBarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    data.visuals.actionLabel?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = PADDING),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            SnackBar(
                                message = data.visuals.message,
                                actionLabel = it,
                                onAction = { data.dismiss() }
                            )
                        }
                    }
                }
            )
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
            var expanded by remember { mutableStateOf(false) }

            when (uiState.cachedNutritionUiModel.isValid) {
                true -> NutrimentCard(
                    nutritionUiModel = uiState.cachedNutritionUiModel,
                    onClick = { expanded = true }
                )

                false -> EmptySelectionDropDownMenu(
                    modifier = Modifier.fillMaxWidth()
                ) { expanded = true }
            }

            if (expanded) {
                DropDownDialog(
                    pagingItems = pagingItems,
                    onDismiss = { expanded = false },
                    itemContent = { nutrition ->
                        NutrimentCard(
                            nutritionUiModel = nutrition,
                            onClick = {
                                onItemSelected(it)
                                expanded = false
                            },
                            onLongClick = { onDeleteSelected(it) }
                        )
                    }
                )
            }

            NutritionDataGroup(
                modifier = Modifier.wrapContentSize(),
                nutritionUiModel = componentUiState.nutritionUiModel,
                onComponentValueChange = onComponentValueChange,
                onReset = onReset,
                onLastItem = { showUpdateDialog.value = true },
                uiComponents = uiComponents,
                errors = componentUiState.errors,
                showErrors = componentUiState.showErrors
            )

            if (error != null) {
                val label = stringResource(id = R.string.okay)
                val errorMessage =
                    error.messageRes?.let { stringResource(id = it) } ?: error.message ?: ""

                LaunchedEffect(error) {
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                        actionLabel = label
                    )
                }
            }
        }

        if (showUpdateDialog.value) {
            Dialog(
                title = stringResource(id = R.string.warning),
                text = stringResource(id = R.string.save_change),
                onDismiss = { showUpdateDialog.value = false },
                onConfirm = {
                    onUpdate()
                    showUpdateDialog.value = false
                    focusManager.clearFocus()
                }
            )
        }
    }
}

@Composable
fun NutritionDataUpdateScreen(
    viewModel: NutritionUpdateViewModel = hiltViewModel()
) {
    val componentUiState by viewModel.uiComponentState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val uiComponents by viewModel.uiComponents.collectAsState()
    val nutriments = viewModel.nutriments.collectAsLazyPagingItems()

    NutritionDataUpdateContent(
        pagingItems = nutriments,
        componentUiState = componentUiState,
        uiComponents = uiComponents,
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
            viewModel.validate()
        },
        onReset = { viewModel.resetError(it) },
        onUpdate = {
            if (componentUiState.isValid) {
                viewModel.update()
            } else {
                viewModel.validate()
                viewModel.showErrors()
            }
        }
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
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            componentUiState = ComponentUiState(),
            uiState = NutritionUpdateUiState(),
            uiComponents = listOf(
                Name(),
                Kcal(),
                Protein(),
                Fat(),
                Carbohydrates(),
                Sugar(),
                Fiber(),
                Alcohol()
            ),
            onItemSelected = {},
            onDeleteSelected = {},
            onComponentValueChange = { _, _ -> },
            onReset = {},
            onUpdate = {}
        )
    }
}
