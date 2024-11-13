package com.example.endo_ai.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.endo_ai.features.auth.presentation.view.AuthScreen
import com.example.endo_ai.features.community.presentation.view.CommunityScreen
import com.example.endo_ai.features.community.presentation.view.JoinCommunityScreen
import com.example.endo_ai.features.community.presentation.viewmodel.CommunityViewModel
import com.example.endo_ai.features.create.CreateCommunityScreen
import com.example.endo_ai.features.scan.presentation.view.ScanDiseaseScreen


@ExperimentalFoundationApi
@Composable
fun SetUpNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CommunityViewModel = hiltViewModel()
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
            route = Screens.JoinCommunityScreen.createRoute("{communityId}"),
            arguments = listOf(
                navArgument("communityId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val communityId = backStackEntry.arguments?.getString("communityId")
            JoinCommunityScreen(
                navController = navController,
                viewModel = viewModel,
                communityId = communityId
            )
        }

    }
}

