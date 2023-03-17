package com.example.hannapp.ui.components


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.R
import com.example.hannapp.data.model.NavigationItem
import com.example.hannapp.navigation.Destination
import com.example.hannapp.navigation.NavigationActions
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun NavigationBar(
    navController: NavHostController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            val currentRoute = currentRoute(navController = navController)

            navigationItems(navController).forEach { item ->
                NavigationBarItem(item, currentRoute)
            }
        }
    }
}

@Composable
private fun navigationItems(navController: NavHostController): List<NavigationItem> {
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    return listOf(
        NavigationItem(
            R.string.database,
            R.drawable.food,
            Destination.DATA.value,
            navigationActions.navigateToData
        ),
        NavigationItem(
            R.string.food_selection,
            R.drawable.eat,
            Destination.SELECTION.value,
            navigationActions.navigateToSelection
        ),
        NavigationItem(
            R.string.calculation,
            R.drawable.calculate,
            Destination.CALCULATION.value,
            navigationActions.navigateToCalculation
        ),
        NavigationItem(R.string.level, R.drawable.sum),
        NavigationItem(R.string.guide, R.drawable.help)
    )
}

@Composable
private fun RowScope.NavigationBarItem(
    item: NavigationItem,
    currentRoute: String?
) {
    NavigationBarItem(
        onClick = item.action,
        icon = {
            Icon(
                painterResource(id = item.icon),
                contentDescription = "",
                modifier = Modifier.size(18.dp)
            )
        },
        label = {
            Text(
                text = stringResource(id = item.label),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall
            )
        },
        selected = currentRoute == item.destination
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun HannappBottomBar_LightMode() {
    HannAppTheme {
        NavigationBar( rememberNavController() )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HannappBottomBar_DarkMode() {
    HannAppTheme {
        NavigationBar( rememberNavController() )
    }
}
