package com.example.hannapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    message: String,
    actionLabel: String,
    onAction: () -> Unit = {}
) {
    Card(
        modifier = modifier.wrapContentSize(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Default.Notifications,
                contentDescription = null
            )
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(onClick = { onAction() }) {
                Text(text = actionLabel)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, device = "id:5.4in FWVGA")
@Composable()
fun SnackBar_LightMode_Preview() {
    HannAppTheme() {
        SnackBar(
            modifier = Modifier.fillMaxWidth(),
            message = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
                " sed diam nonumy eirmod tempor inviduntut labore et dolore magna aliquyam erat," +
                " sed diam voluptua.",
            actionLabel = "okay"
        )
    }
}
