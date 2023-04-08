package com.example.hannapp.ui.mood

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

enum class Mood(val value: String, val color: Color, @DrawableRes val drawable: Int){
    RED("concerned", Color.Red, R.drawable.concerned),
    YELLOW("neutral", Color.Yellow, R.drawable.neutral),
    GREEN("satisfied", Color.Green, R.drawable.smiling)
}

@Composable
fun MoodLight(mood: Mood){
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        color = MaterialTheme.colorScheme.surface
    ) {
        Icon(painter = painterResource(id = mood.drawable), contentDescription = mood.value, tint = mood.color)
    }
}

@Preview
@Composable
fun NeutralMood_Light(){
    HannAppTheme {
        MoodLight(mood = Mood.RED)
    }
}

@Preview
@Composable
fun HappyMoodLight_Light(){
    HannAppTheme {
        MoodLight(mood = Mood.YELLOW)
    }
}

@Preview
@Composable
fun VerySatisfiedMoodLight_Light(){
    HannAppTheme {
        MoodLight(mood = Mood.GREEN)
    }
}