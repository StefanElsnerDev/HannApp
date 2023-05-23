package com.example.hannapp.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.components.NutrimentHistoryCard
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun NutrimentHistoryContent(
    modifier: Modifier,
    nutriments: List<NutrimentUiLogModel>,
    onLongClick: (NutrimentUiLogModel) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Surface(
        modifier = modifier
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        LaunchedEffect(nutriments) {
            if (listState.layoutInfo.totalItemsCount - 1 < nutriments.size) {
                listState.animateScrollToItem(nutriments.size)
            }
        }

        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            state = listState
        ) {
            items(nutriments) { nutrimentUiLogModel ->
                NutrimentHistoryCard(
                    modifier = Modifier.padding(PADDING),
                    nutrimentUiLogModel = nutrimentUiLogModel,
                    onClick = {},
                    onLongClick = { onLongClick(it) }
                )
            }
        }
    }
}

@Preview
@Composable
fun NutrimentHistoryContent_LightMode() {
    HannAppTheme {
        NutrimentHistoryContent(
            modifier = Modifier,
            nutriments = listOf(
                NutrimentUiLogModel(
                    id = 1,
                    nutrition = NutritionUiModel(
                        name = "Peach"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    createdAt = 1681801313,
                    modifiedAt = 1999801313
                ),
                NutrimentUiLogModel(
                    id = 2,
                    nutrition = NutritionUiModel(
                        name = "Apple"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    createdAt = 1681801313,
                    modifiedAt = null
                ),
                NutrimentUiLogModel(
                    id = 2,
                    nutrition = NutritionUiModel(
                        name = "Chocolate"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    createdAt = 1681801313,
                    modifiedAt = 1999801313
                )
            )
        )
    }
}
