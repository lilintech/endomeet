package com.example.endo_ai.features.scan.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalysisResultDto(
    @SerialName("findings")
    val findings: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("recommendation")
    val recommendation: String
)
