package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.theme.HannAppTheme
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun NutrimentHistoryCard(
    modifier: Modifier,
    nutrimentUiLogModel: NutrimentUiLogModel,
    onClick: (NutrimentUiLogModel) -> Unit,
    onLongClick: (NutrimentUiLogModel) -> Unit
) {
    Column(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(nutrimentUiLogModel) },
                onLongClick = { onLongClick(nutrimentUiLogModel) })
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = nutrimentUiLogModel.nutrition.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = "${nutrimentUiLogModel.nutrition.protein} g",
                onValueChange = { _ -> },
                label = {
                    Text(text = stringResource(id = R.string.protein))
                },
                textStyle = MaterialTheme.typography.labelMedium,
                enabled = false
            )

            TextField(
                modifier = Modifier.weight(1f),
                value = "${nutrimentUiLogModel.nutrition.fat} g",
                onValueChange = { _ -> },
                label = {
                    Text(text = stringResource(id = R.string.fad))
                },
                textStyle = MaterialTheme.typography.labelMedium,
                enabled = false
            )

            TextField(
                modifier = Modifier.weight(1f),
                value = "${nutrimentUiLogModel.nutrition.kcal} kcal",
                onValueChange = { _ -> },
                label = {
                    Text(text = stringResource(id = R.string.energy))
                },
                textStyle = MaterialTheme.typography.labelMedium,
                enabled = false
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = "${nutrimentUiLogModel.quantity} ${nutrimentUiLogModel.unit}",
                onValueChange = { _ -> },
                label = {
                    Text(text = stringResource(id = R.string.quantity))
                },
                textStyle = MaterialTheme.typography.labelMedium,
                enabled = false
            )

            TextField(
                modifier = Modifier.weight(1f),
                value = SimpleDateFormat(
                    "k:m",
                    Locale.getDefault()
                ).format(nutrimentUiLogModel.timeStamp),
                onValueChange = { _ -> },
                label = {
                    Text(text = stringResource(id = R.string.time))
                },
                textStyle = MaterialTheme.typography.labelMedium,
                enabled = false
            )
        }
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NutrimentHistoryCard_LightMode() {
    HannAppTheme {
        NutrimentHistoryCard(
            modifier = Modifier,
            nutrimentUiLogModel = NutrimentUiLogModel(
                nutrition = NutritionUiModel(
                    name = "Cola",
                    protein = "1.23",
                    fat = "5.6",
                    kcal = "8.9"
                ),
                quantity = 12.345,
                unit = "g",
                timeStamp = 1681801313
            ),
            onClick = {},
            onLongClick = {}
        )
    }
}
