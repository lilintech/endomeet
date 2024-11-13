package com.example.endo_ai.features.community.presentation.state

import com.example.endo_ai.features.community.domain.model.Community

data class CommunityState(
    val isLoading: Boolean = false,
    val communities: List<Community> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)
