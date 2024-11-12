package com.example.endo_ai.core.sharedComposables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.endo_ai.navigation.Screens

@Composable

fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val screens = Screens.BottomNavScreen.items

    BottomNavigation(
        modifier = modifier,
        elevation = 8.dp,
        backgroundColor = Color.LightGray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screens ->
            BottomNavigationItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = when (screens) {
                            Screens.Community -> Icons.Default.ChatBubbleOutline
                            Screens.ScanDisease -> Icons.Default.DocumentScanner
                            Screens.CreateCommunity -> Icons.Default.AddCircleOutline
                            else -> Icons.Default.ChatBubbleOutline
                        },
                        contentDescription = screens.route
                    )
                },
                label = {
                    Text(
                        text = when (screens) {
                            Screens.Community -> "Community"
                            Screens.ScanDisease -> "Scan Disease"
                            Screens.CreateCommunity -> "Create community"
                            else -> ""
                        },
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                },
                selected = currentRoute == screens.route,
                onClick = {
                    navController.navigate(screens.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.DarkGray
            )
        }
    }
}

