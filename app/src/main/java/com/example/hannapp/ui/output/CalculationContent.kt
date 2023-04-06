package com.example.hannapp.ui.output

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.mood.MoodLight
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun CalculationContent(
    modifier: Modifier = Modifier,
    mood: Mood
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
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
                text = "1234.56 ml",
                label = "Milchausschuss"
            )
            OutputCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING),
                drawable = R.drawable.spoon,
                text = "1234.56 g",
                label = "Maltozugabe"
            )
            MoodLight(mood = mood)
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
            mood = Mood.GREEN
        )
    }
}
