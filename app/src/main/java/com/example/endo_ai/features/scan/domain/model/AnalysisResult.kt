package com.example.endo_ai.features.scan.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalysisResult(
    val findings: String,
    val summary: String,
    val recommendation: String
)
