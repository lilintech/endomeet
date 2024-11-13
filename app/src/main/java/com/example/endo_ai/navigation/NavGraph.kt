package com.example.endo_ai.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.endo_ai.features.auth.presentation.view.AuthScreen
import com.example.endo_ai.features.community.CommunityData
import com.example.endo_ai.features.community.CommunityScreen
import com.example.endo_ai.features.community.JoinCommunityScreen
import com.example.endo_ai.features.create.CreateCommunityScreen
import com.example.endo_ai.features.scan.presentation.view.ScanDiseaseScreen


@ExperimentalFoundationApi
@Composable
fun SetUpNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        startDestination = Screens.AuthScreen.route,
        navController = navController
    ) {
        composable(route = Screens.AuthScreen.route) { AuthScreen(
            onAuthSuccess = {
                navController.navigate(Screens.Community.route){
                    popUpTo(Screens.AuthScreen.route){ inclusive = true }
                }
            }
        ) }
        composable(route = Screens.ScanDisease.route) { ScanDiseaseScreen(navController = navController) }
        composable(route = Screens.Community.route) { CommunityScreen(navController = navController) }
        composable(route = Screens.CreateCommunity.route) { CreateCommunityScreen(navController = navController) }
        composable(
            route = Screens.JoinCommunityScreen.route,
            arguments = listOf(
                navArgument("communityTitle") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val communityTitle = backStackEntry.arguments?.getString("communityTitle")
            val communityData = getCommunityData(communityTitle)
            JoinCommunityScreen(navController = navController, item = communityData)
        }

    }
}

private fun getCommunityData(communityTitle: String?): CommunityData {
    return CommunityData(
        title = communityTitle ?: "Default Community",
        description = "Community description",
        icon = Icons.Default.People
    )
}