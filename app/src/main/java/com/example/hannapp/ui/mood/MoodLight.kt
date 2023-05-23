package com.example.hannapp.ui.mood

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

enum class Mood(val value: String, @DrawableRes val drawable: Int) {
    RED("concerned", R.drawable.concerned),
    YELLOW("neutral", R.drawable.neutral),
    GREEN("satisfied", R.drawable.smiling)
}

@Composable
fun MoodLight(
    modifier: Modifier = Modifier,
    mood: Mood
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = mood.drawable),
        contentDescription = mood.value,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview
@Composable
fun NeutralMood_Light() {
    HannAppTheme {
        MoodLight(mood = Mood.RED)
    }
}

@Preview
@Composable
fun HappyMoodLight_Light() {
    HannAppTheme {
        MoodLight(mood = Mood.YELLOW)
    }
}

@Preview
@Composable
fun VerySatisfiedMoodLight_Light() {
    HannAppTheme {
        MoodLight(mood = Mood.GREEN)
    }
}
