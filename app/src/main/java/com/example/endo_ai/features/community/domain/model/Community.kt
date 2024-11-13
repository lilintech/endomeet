package com.example.endo_ai.features.community.domain.model

data class Community(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val creatorId: String = "",
    val creatorName: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val members: List<String> = emptyList()
)
