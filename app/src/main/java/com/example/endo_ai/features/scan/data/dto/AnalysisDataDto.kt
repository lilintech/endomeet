package com.example.endo_ai.features.scan.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalysisDataDto(
    @SerialName("result")
    val result: AnalysisResultDto
)
