package com.example.hannapp.ui.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Button(modifier: Modifier, icon: ImageVector, onClick: ()->Unit){
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(icon, "")
    }
}