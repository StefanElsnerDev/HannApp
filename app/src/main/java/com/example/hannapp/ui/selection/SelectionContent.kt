package com.example.hannapp.ui.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.components.NutrimentCard
import com.example.hannapp.ui.dropdown.DropDownDialog
import com.example.hannapp.ui.dropdown.EmptySelectionDropDownMenu
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.flow.flowOf


@Composable
fun SelectionContent(
    modifier: Modifier,
    uiState: NutritionUiState,
    snackBarHost: SnackbarHostState,
    onClickBoxClick: () -> Unit,
    quantity: String,
    onQuantityChanged: (String) -> Unit,
    selectedNutriment: NutritionUiModel,
    onNutrimentChanged: (NutritionUiModel) -> Unit,
    pagingItems: LazyPagingItems<NutritionUiModel>,
) {
    Surface(
        modifier = modifier.wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .wrapContentSize()
                .padding(PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var expanded by remember { mutableStateOf(false) }

            uiState.errorMessage?.let {
                val errorMessageText: String = it
                val retryMessageText = stringResource(id = R.string.okay)

                LaunchedEffect(errorMessageText, retryMessageText, snackBarHost) {
                    snackBarHost.showSnackbar(
                        message = errorMessageText,
                        actionLabel = retryMessageText
                    )
                }
            }

            when (uiState.isSelectionValid) {
                false -> EmptySelectionDropDownMenu(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onClickBoxClick()
                    expanded = true
                }

                true -> NutrimentCard(
                    nutritionUiModel = selectedNutriment,
                    onClick = {
                        onClickBoxClick()
                        expanded = true
                    },
                    onLongClick = {}
                )
            }

            InputField(
                value = quantity,
                onValueChange = { onQuantityChanged(it) },
                modifier = Modifier,
                label = stringResource(id = R.string.quantity),
                isError = false
            )

            if (expanded) {
                DropDownDialog(
                    pagingItems = pagingItems,
                    onDismiss = { expanded = false },
                    itemContent = {
                        NutrimentCard(
                            nutritionUiModel = it,
                            onClick = { nutriment ->
                                onNutrimentChanged(nutriment)
                                expanded = false
                            },
                            onLongClick = {}
                        )
                    }
                )
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun SelectionContent_LightMode() {
    HannAppTheme {
        SelectionContent(
            modifier = Modifier,
            uiState = NutritionUiState(),
            snackBarHost = SnackbarHostState(),
            quantity = "",
            onQuantityChanged = {},
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            onClickBoxClick = {},
            selectedNutriment = NutritionUiModel(),
            onNutrimentChanged = {}
        )
    }
}
