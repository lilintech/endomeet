package com.example.endo_ai.navigation

sealed class Screens(
    val route: String,
) {
    data object AuthScreen : Screens("auth")
    data object Community: Screens("community_screen")
    data object CreateCommunity: Screens("create_community_screen")
    data object ScanDisease: Screens("scan_disease_screen")
    data object JoinCommunityScreen: Screens("join_community_screen/{communityTitle}") {
        fun createRoute(communityTitle: String) = "join_community_screen/$communityTitle"
    }

    object BottomNavScreen {
        val items = listOf(
            Community,
            CreateCommunity,
            ScanDisease
        )
    }

}