package com.example.endo_ai.features.community.presentation.event

import android.net.Uri

sealed class CommunityUiEvent {
    data class CreateCommunity(
        val title: String,
        val description: String,
        val imageUrl: Uri? = null
    ) : CommunityUiEvent()

    data class JoinCommunity(val communityId: String) : CommunityUiEvent()
    data class LeaveCommunity(val communityId: String) : CommunityUiEvent()
    data object ClearError : CommunityUiEvent()
}