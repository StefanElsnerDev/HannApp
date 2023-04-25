package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hannapp.R
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NutrimentCard(
    modifier: Modifier,
    nutritionUiModel: NutritionUiModel,
    onClick: (NutritionUiModel) -> Unit,
    onLongClick: (NutritionUiModel) -> Unit
) {
    Row(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(nutritionUiModel) },
                onLongClick = { onLongClick(nutritionUiModel) })
            .height(IntrinsicSize.Min)
            .padding(PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = nutritionUiModel.name,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 32.sp),
            maxLines = 1
        )

        Column(
            modifier = Modifier.weight(0.3f)
        ) {
            NutrimentTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "${nutritionUiModel.kcal} kcal",
                label = { Text(text = stringResource(id = R.string.energy)) }
            )

            NutrimentTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "${nutritionUiModel.protein} g",
                label = { Text(text = stringResource(id = R.string.protein)) }
            )
        }
        Column(
            modifier = Modifier.weight(0.3f)
        ) {
            NutrimentTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "${nutritionUiModel.carbohydrates} g",
                label = { Text(text = stringResource(id = R.string.carbohydrates)) }
            )
            NutrimentTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "${nutritionUiModel.fat} g",
                label = { Text(text = stringResource(id = R.string.fat)) }
            )
        }
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun NutrimentCard_LightMode() {
    HannAppTheme {
        NutrimentCard(
            modifier = Modifier
                .fillMaxWidth(),
            nutritionUiModel = NutritionUiModel(
                name = "Cola",
                protein = "1.23",
                fat = "5.6",
                kcal = "8.9"
            ),
            onClick = {},
            onLongClick = {}
        )
    }
}
