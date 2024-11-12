package com.example.endo_ai.features.scan.presentation.state

import android.net.Uri
import com.example.endo_ai.features.scan.domain.model.AnalysisResult

data class ImageAnalysisUiState(
    val isLoading: Boolean = false,
    val analysisResult: AnalysisResult? = null,
    val errorMessage: String? = "",
    val selectedImageUri: Uri? = null
)