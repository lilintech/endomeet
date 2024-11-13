package com.example.endo_ai

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.endo_ai.core.sharedComposables.BottomNavBar
import com.example.endo_ai.navigation.Screens
import com.example.endo_ai.navigation.SetUpNavGraph
import com.example.endo_ai.ui.theme.ThemeUtils.EndoaiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EndoaiTheme {
                Scaffold(
                    bottomBar = {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route
                        val showBottomBar =
                            currentRoute in Screens.BottomNavScreen.items.map { it.route }
                        if (showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) { paddingValues ->
                    SetUpNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

