package com.example.hannapp.ui.output

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.mood.MoodLight
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun CalculationContent(
    modifier: Modifier = Modifier,
    milkDiscard: String,
    isDiscardExceeding: Boolean,
    maltoSubstitution: String,
    mood: Mood
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .wrapContentHeight()
        ) {
            OutputCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING),
                drawable = R.drawable.liter,
                text = "$milkDiscard ml",
                label = stringResource(id = R.string.milk_discard)
            )
            OutputCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING),
                drawable = R.drawable.spoon,
                text = "$maltoSubstitution g",
                label = stringResource(id = R.string.malto_addition)
            )
            MoodLight(
                modifier = Modifier.size(128.dp)
                    .padding(PADDING),

                mood = mood
            )
        }
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun CalculationPreview_LightMode() {
    HannAppTheme {
        CalculationContent(
            modifier = Modifier.fillMaxSize(),
            milkDiscard = "123.4",
            isDiscardExceeding = false,
            maltoSubstitution = "89.2",
            mood = Mood.YELLOW
        )
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun CalculationPreview_Exceeding_LightMode() {
    HannAppTheme {
        CalculationContent(
            modifier = Modifier.fillMaxSize(),
            milkDiscard = "123.4",
            isDiscardExceeding = true,
            maltoSubstitution = "89.2",
            mood = Mood.YELLOW
        )
    }
}

@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun CalculationPreview_DarkMode() {
    HannAppTheme {
        CalculationContent(
            milkDiscard = "123.4",
            isDiscardExceeding = false,
            maltoSubstitution = "89.2",
            mood = Mood.GREEN
        )
    }
}
