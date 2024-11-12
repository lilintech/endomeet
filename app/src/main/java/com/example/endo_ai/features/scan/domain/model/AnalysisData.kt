package com.example.endo_ai.features.scan.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalysisData(
    val results: AnalysisResult
)
