package com.example.hannapp.ui.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.ui.dropdown.DropDownDialog
import com.example.hannapp.ui.dropdown.SimpleDropDownItem
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.flow.flowOf


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectionContent(
    modifier: Modifier,
    uiState: NutritionUiState,
    snackBarHost: SnackbarHostState,
    onClickBoxClick: () -> Unit,
    pagingItems: LazyPagingItems<Nutrition>,
    onItemSelected: (String) -> Unit
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
            var input by rememberSaveable { mutableStateOf("") }
            var selectedItem by rememberSaveable { mutableStateOf("") }
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

            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                OutlinedTextField(
                    value = selectedItem.ifBlank { "No Data" },
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    textStyle = MaterialTheme.typography.titleMedium,
                    label = { Text(text = stringResource(id = R.string.food_selection)) },
                    trailingIcon = {
                        if (!expanded) Icon(
                            Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                )

                //ClickBox
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onClickBoxClick()
                            expanded = true
                        },
                    color = Color.Transparent,
                ) {}
            }

            InputField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier,
                label = stringResource(id = R.string.quantity),
                isError = false
            )

            if (expanded) {
                DropDownDialog(
                    pagingItems = pagingItems,
                    onDismiss = { expanded = false },
                    itemContent = {
                        SimpleDropDownItem(
                            item = it,
                            onClick = { item ->
                                onItemSelected(item.toString())
                                selectedItem = item.toString()
                                expanded = false
                            }
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
            pagingItems = flowOf(PagingData.from(listOf(Nutrition()))).collectAsLazyPagingItems(),
            onClickBoxClick = {},
            onItemSelected = {}
        )
    }
}