package com.example.hannapp.ui.output

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun OutputCard(
    modifier: Modifier,
    @DrawableRes drawable: Int,
    label: String,
    text: String,
    isError: Boolean = false
) {
    Row(
        modifier = modifier.width(192.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = "",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        OutlinedTextField(
            value = text,
            onValueChange = {},
            readOnly = true,
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = label) },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = stringResource(id = R.string.discard_exceeding))
                }
            }
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun OutputCardPreview_LightMode() {
    HannAppTheme {
        OutputCard(
            modifier = Modifier,
            drawable = R.drawable.liter,
            label = "Milchausschuss",
            text = "1234,56 ml"
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun OutputCardPreview_Is_Exceeding_LightMode() {
    HannAppTheme {
        OutputCard(
            modifier = Modifier,
            drawable = R.drawable.liter,
            label = "Milchausschuss",
            text = "1234,56 ml",
            isError = true
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OutputCardPreview_DarkMode() {
    HannAppTheme {
        OutputCard(
            modifier = Modifier,
            drawable = R.drawable.liter,
            label = "Milchausschuss",
            text = "1234,56 ml"
        )
    }
}
