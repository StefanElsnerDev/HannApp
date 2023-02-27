package com.example.hannapp.ui.output

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun CalculationScreen(mood: Mood){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        OutputCard(modifier = Modifier.fillMaxWidth(), drawable = R.drawable.liter, text = "1234.56 ml", label = "Milchausschuss")
        OutputCard(modifier = Modifier.fillMaxWidth(), drawable = R.drawable.spoon, text = "1234.56 g", label = "Maltozugabe")
        MoodLight(mood = mood)
    }
}

@Preview(
    backgroundColor = 0xFFD0BCFF,
    heightDp = 800,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CalculationPreview_DarkMode(){
    HannAppTheme {
        CalculationScreen(Mood.GREEN)
    }
}

@Preview(
    backgroundColor = 0xFFD0BCFF,
    heightDp = 800,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun CalculationPreview_LightMode(){
    HannAppTheme {
        CalculationScreen(Mood.YELLOW)
    }
}
