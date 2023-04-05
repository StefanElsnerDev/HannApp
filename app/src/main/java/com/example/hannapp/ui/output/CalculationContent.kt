package com.example.hannapp.ui.output

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.mood.MoodLight
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun CalculationContent(
    modifier: Modifier = Modifier,
    mood: Mood
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().padding(32.dp)
    ) {
        OutputCard(
            modifier = Modifier.fillMaxWidth(),
            drawable = R.drawable.liter,
            text = "1234.56 ml",
            label = "Milchausschuss"
        )
        OutputCard(
            modifier = Modifier.fillMaxWidth(),
            drawable = R.drawable.spoon,
            text = "1234.56 g",
            label = "Maltozugabe"
        )
        MoodLight(mood = mood)
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CalculationPreview_LightMode() {
    HannAppTheme {
        CalculationContent(
            mood = Mood.YELLOW
        )
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CalculationPreview_DarkMode() {
    HannAppTheme {
        CalculationContent(
            mood = Mood.GREEN
        )
    }
}
